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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.domain.entity.recruitment.RecruitmentEntity
import team.retum.jobis_android.feature.main.home.ApplyCompaniesItemShape
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.util.compose.component.Header
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
    navigateToRecruitmentDetails: (Long) -> Unit,
    isWinterIntern: Boolean,
    recruitmentsScreenViewModel: RecruitmentsScreenViewModel = hiltViewModel(),
) {
    val state by recruitmentsScreenViewModel.collectAsState()
    val recruitments = recruitmentsScreenViewModel.recruitments
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val onFilterClicked: () -> Unit = {
        coroutineScope.launch {
            sheetState.show()
        }
    }

    LaunchedEffect(Unit) {
        with(recruitmentsScreenViewModel) {
            setWinterIntern(isWinterIntern)
            snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.callNextPageByPosition()
            fetchRecruitments()
            fetchRecruitmentCount()
            observeName()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            RecruitmentFilter(sheetState = sheetState.isVisible) { jobCode: Long?, techCode: String? ->
                recruitmentsScreenViewModel.setCodes(
                    jobCode = jobCode,
                    techCode = techCode,
                )
                coroutineScope.launch {
                    sheetState.hide()
                    lazyListState.animateScrollToItem(0)
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
                text = stringResource(
                    id = if (isWinterIntern) {
                        R.string.recruitments_winter_interns
                    } else {
                        R.string.recruitments_header
                    },
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            RecruitmentInput(
                name = recruitmentsScreenViewModel.name,
                jobCode = state.jobCode,
                techCode = state.techCode,
                onFilterClicked = onFilterClicked,
                onNameChanged = {
                    recruitmentsScreenViewModel.setName(it)
                    coroutineScope.launch {
                        lazyListState.scrollToItem(
                            index = 0,
                            scrollOffset = 0,
                        )
                    }
                },
            )
            Recruitments(
                lazyListState = lazyListState,
                recruitments = recruitments,
                onBookmarked = recruitmentsScreenViewModel::bookmark,
                navigateToRecruitmentDetails = navigateToRecruitmentDetails,
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
    onNameChanged: (String) -> Unit,
) {
    val searchResultTextAlpha = if (name.isNullOrBlank()) 0f else 1f
    val filterAppliedTextAlpha = if (jobCode != null || techCode != null) 1f else 0f

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(modifier = Modifier.weight(0.9f)) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                onValueChanged = onNameChanged,
                value = name ?: "",
                hint = stringResource(id = R.string.recruitments_filter_hint),
            )
        }
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
                modifier = Modifier
                    .weight(1f)
                    .alpha(alpha = searchResultTextAlpha),
                text = "${stringResource(id = R.string.search_result)} ${name ?: ""}",
                color = JobisColor.Gray600,
            )
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
    recruitments: SnapshotStateList<RecruitmentEntity>,
    onBookmarked: (recruitmentEntity: RecruitmentEntity, index: Int) -> Unit,
    navigateToRecruitmentDetails: (Long) -> Unit,
) {
    if (recruitments.isNotEmpty()) {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            itemsIndexed(recruitments) { index, recruitment ->
                val trainPay = DecimalFormat("#,###").format(recruitment.trainPay)
                with(recruitment) {
                    Recruitment(
                        recruitId = recruitId,
                        imageUrl = companyProfileUrl,
                        hiringJobs = hiringJobs,
                        isBookmarked = bookmarked,
                        companyName = companyName,
                        trainPay = stringResource(
                            id = R.string.recruitments_train_pay,
                            trainPay,
                        ),
                        isMilitarySupported = military,
                        onBookmarked = { onBookmarked(recruitment, index) },
                        onItemClicked = { navigateToRecruitmentDetails(recruitId) },
                    )
                }
            }
        }
    }
}

@Composable
private fun Recruitment(
    recruitId: Long,
    imageUrl: String,
    hiringJobs: String,
    isBookmarked: Boolean,
    companyName: String,
    trainPay: String,
    isMilitarySupported: Boolean,
    onBookmarked: () -> Unit,
    onItemClicked: () -> Unit,
) {
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
                start = 8.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
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
                            .skeleton(hiringJobs.isBlank()),
                        text = hiringJobs,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (recruitId != 0L) {
                        Image(
                            modifier = Modifier
                                .size(18.dp)
                                .jobisClickable(onClick = onBookmarked),
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
        }
    }
}
