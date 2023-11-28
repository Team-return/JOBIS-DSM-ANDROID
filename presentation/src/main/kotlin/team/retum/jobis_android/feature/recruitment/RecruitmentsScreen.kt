package team.retum.jobis_android.feature.recruitment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.launch
import team.retum.jobis_android.feature.main.ApplyCompaniesItemShape
import team.retum.jobis_android.navigation.NavigationProperties
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.bookmark.BookmarkViewModel
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentUiModel
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisMediumIconButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.Animated
import team.returm.jobisdesignsystem.util.JobisSize
import team.returm.jobisdesignsystem.util.jobisClickable
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun RecruitmentsScreen(
    putString: (String, String) -> Unit,
    navigateToRecruitmentDetails: (Long) -> Unit,
    isWinterIntern: Boolean,
    recruitmentViewModel: RecruitmentViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        recruitmentViewModel.setIsWinterIntern(isWinterIntern)
        with(recruitmentViewModel) {
            resetPage()
            fetchRecruitments()
            fetchRecruitmentCount()
        }
    }

    val state by recruitmentViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    val recruitments = state.recruitments

    val coroutineScope = rememberCoroutineScope()

    val onFilterClicked: () -> Unit = {
        coroutineScope.launch {
            sheetState.show()
        }
    }

    val lazyListState = rememberLazyListState()
    var checkRecruitment by remember { mutableStateOf(false) }

    LaunchedEffect(checkRecruitment) {
        if (checkRecruitment) {
            with(recruitmentViewModel) {
                setPage()
                fetchRecruitments()
            }
        }
    }

    LaunchedEffect(Unit) {
        recruitmentViewModel.addRecruitmentsDummy()
    }

    ModalBottomSheetLayout(
        sheetContent = {
            RecruitmentFilter(sheetState = sheetState.isVisible) { jobCode: Long?, techCodes: String ->
                coroutineScope.launch {
                    with(recruitmentViewModel) {
                        setJobCode(jobCode)
                        setTechCode(techCodes)
                        initRecruitments()
                        fetchRecruitmentCount()
                        fetchRecruitments()
                    }
                    sheetState.hide()
                }
            }
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 48.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(
                text = if (!isWinterIntern) stringResource(id = R.string.recruitments_header)
                else stringResource(id = R.string.recruitments_winter_interns)
            )
            Spacer(modifier = Modifier.height(12.dp))
            RecruitmentInput(
                name = state.name,
                jobCode = state.jobCode,
                techCode = state.techCode,
                onFilterClicked = onFilterClicked,
                onKeywordChanged = recruitmentViewModel::setName,
            )
            Recruitments(
                lazyListState = lazyListState,
                recruitmentUiModels = recruitments,
                bookmarkViewModel = bookmarkViewModel,
                putString = putString,
                navigateToRecruitmentDetails = navigateToRecruitmentDetails,
                checkRecruitment = { checkRecruitment = it },
                recruitmentCount = state.recruitmentCount,
                pageCount = state.page,
            )
        }
    }
}

@Composable
private fun ColumnScope.RecruitmentInput(
    name: String?,
    jobCode: Long?,
    techCode: String?,
    onFilterClicked: () -> Unit,
    onKeywordChanged: (String) -> Unit,
) {
    val searchResultTextAlpha = if (name.isNullOrBlank()) 0f else 1f
    val filterAppliedTextAlpha = if (jobCode != null || techCode != null) 1f else 0f

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(0.9f)) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                onValueChanged = onKeywordChanged,
                value = name ?: "",
                hint = stringResource(id = R.string.recruitments_filter_hint),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        JobisMediumIconButton(
            drawable = R.drawable.ic_filter,
            color = JobisButtonColor.MainSolidColor,
            onClick = onFilterClicked,
            shape = RoundedCornerShape(size = 4.dp),
            imageContentDescription = stringResource(id = R.string.content_description_filter),
        )
    }
    Animated(!name.isNullOrBlank() || techCode != null || jobCode != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Caption(
                modifier = Modifier.alpha(alpha = searchResultTextAlpha),
                text = stringResource(id = R.string.search_result),
                color = JobisColor.Gray600,
            )
            Caption(text = name ?: "")
            Spacer(modifier = Modifier.weight(1f))
            Caption(
                modifier = Modifier.alpha(alpha = filterAppliedTextAlpha),
                text = stringResource(id = R.string.filter_applied),
                color = JobisColor.Gray600,
            )
        }
    }
}

@Composable
private fun Recruitments(
    lazyListState: LazyListState,
    recruitmentUiModels: List<RecruitmentUiModel>,
    bookmarkViewModel: BookmarkViewModel,
    putString: (String, String) -> Unit,
    navigateToRecruitmentDetails: (Long) -> Unit,
    checkRecruitment: (Boolean) -> Unit,
    recruitmentCount: Long,
    pageCount: Int,
) {
    val onBookmarked = { index: Int, recruitmentId: Long, setBookmark: () -> Unit ->
        recruitmentUiModels[index].bookmarked = !recruitmentUiModels[index].bookmarked
        bookmarkViewModel.bookmarkRecruitment(recruitmentId = recruitmentId)
        setBookmark()
    }

    val onRecruitmentClicked = { recruitment: RecruitmentUiModel ->
        putString(
            NavigationProperties.COMPANY_NAME,
            recruitment.companyName,
        )
        navigateToRecruitmentDetails(recruitment.recruitId)
    }

    if (recruitmentUiModels.isNotEmpty()) {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            itemsIndexed(recruitmentUiModels) { index, recruitment ->
                val position = recruitment.jobCodeList.replace(',', '/')
                val trainPay = DecimalFormat("#,###").format(recruitment.trainPay)

                var isBookmarked by remember { mutableStateOf(recruitment.bookmarked) }

                val setBookmark = {
                    isBookmarked = !isBookmarked
                }

                Recruitment(
                    recruitId = recruitment.recruitId,
                    imageUrl = recruitment.companyProfileUrl,
                    position = position,
                    isBookmarked = isBookmarked,
                    companyName = recruitment.companyName,
                    trainPay = if (recruitment.trainPay != 0L) {
                        stringResource(
                            id = R.string.recruitments_train_pay,
                            trainPay,
                        )
                    } else {
                        ""
                    },
                    isMilitarySupported = recruitment.military,
                    onBookmarked = { onBookmarked(index, recruitment.recruitId, setBookmark) },
                    onItemClicked = {
                        if (recruitment.recruitId != 0L) {
                            onRecruitmentClicked(recruitment)
                        }
                    },
                )
                if (recruitment == recruitmentUiModels.last() && pageCount.toLong() != recruitmentCount && recruitmentCount != 1L) {
                    checkRecruitment(true)
                }
            }
        }
    } else {
        checkRecruitment(true)
    }
    checkRecruitment(false)
}

@Composable
private fun Recruitment(
    recruitId: Long,
    imageUrl: String,
    position: String,
    isBookmarked: Boolean,
    companyName: String,
    trainPay: String,
    isMilitarySupported: Boolean,
    onBookmarked: () -> Unit,
    onItemClicked: () -> Unit,
) {
    var bookmarked = isBookmarked
    val bookmarkIcon = if (isBookmarked) {
        R.drawable.ic_bookmarked_filled
    } else {
        R.drawable.ic_bookmarked_outlined
    }

    val militaryIcon = if (isMilitarySupported) {
        R.drawable.ic_military_filled
    } else {
        R.drawable.ic_military_outlined
    }

    val onBookmarkClicked = {
        onBookmarked()
        bookmarked = !bookmarked
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = ApplyCompaniesItemShape,
            )
            .clip(shape = ApplyCompaniesItemShape)
            .background(color = JobisColor.Gray100)
            .jobisClickable(
                rippleEnabled = true,
                onClick = onItemClicked,
            ),
    ) {
        Row(
            modifier = Modifier.padding(
                end = 20.dp,
                top = 8.dp,
                bottom = 8.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = JobisSize.Shape.Large)
                    .skeleton(show = imageUrl.isEmpty()),
                model = imageUrl,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Body2(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .skeleton(position.isBlank()),
                        text = position,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (recruitId != 0L) {
                        Image(
                            modifier = Modifier
                                .size(18.dp)
                                .jobisClickable(onClick = onBookmarkClicked),
                            painter = painterResource(id = bookmarkIcon),
                            contentDescription = stringResource(id = R.string.content_description_bookmark),
                        )
                    }
                }
                Caption(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .defaultMinSize(minWidth = 80.dp)
                        .skeleton(companyName.isBlank()),
                    text = companyName,
                    color = JobisColor.Gray600,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Caption(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 40.dp)
                            .skeleton(trainPay.isBlank()),
                        text = trainPay,
                    )
                    if (recruitId != 0L) {
                        Image(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = militaryIcon),
                            contentDescription = stringResource(id = R.string.content_description_military),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}
