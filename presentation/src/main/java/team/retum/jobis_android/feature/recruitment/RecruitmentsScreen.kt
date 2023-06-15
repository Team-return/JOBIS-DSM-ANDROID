package team.retum.jobis_android.feature.recruitment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.launch
import team.retum.jobis_android.contract.RecruitmentSideEffect
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobis_android.util.compose.skeleton
import team.retum.jobis_android.viewmodel.bookmark.BookmarkViewModel
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentUiModel
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.button.JobisMediumIconButton
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun RecruitmentsScreen(
    navController: NavController,
    recruitmentViewModel: RecruitmentViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
) {

    val state by recruitmentViewModel.container.stateFlow.collectAsState()

    val recruitments = remember { mutableStateListOf<RecruitmentUiModel>() }

    LaunchedEffect(Unit) {
        recruitmentViewModel.container.sideEffectFlow.collect {
            when (it) {
                is RecruitmentSideEffect.SuccessFetchRecruitments -> {
                    recruitments.addAll(it.recruitments)
                }

                else -> {

                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            RecruitmentFilter { jobCode, techCode ->
                coroutineScope.launch {
                    sheetState.hide()
                    recruitmentViewModel.setJobCode(jobCode)
                    recruitmentViewModel.setTechCode(techCode)
                    recruitmentViewModel.fetchRecruitments()
                    recruitmentViewModel.setPage(1)
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
                text = stringResource(id = R.string.search_recruitment_header),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Filter {
                coroutineScope.launch {
                    sheetState.show()
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.alpha(if (state.name != null) 1f else 0f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Caption(
                        text = stringResource(id = R.string.search_result),
                        color = JobisColor.Gray600,
                    )
                    Caption(text = " ${state.name}")
                }
                Caption(
                    modifier = Modifier.alpha(
                        if (state.jobCode != null || state.techCode != null) 1f
                        else 0f,
                    ),
                    text = stringResource(id = R.string.filter_applied)
                )
            }
            Recruitments(
                recruitmentUiModels = recruitments,
                recruitmentViewModel = recruitmentViewModel,
                bookmarkViewModel = bookmarkViewModel,
                navController = navController,
            )
        }
    }
}

@Composable
internal fun Header(
    text: String,
) {
    Column {
        Body2(
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    alignment = Alignment.Start,
                ),
            text = text,
            color = JobisColor.Gray600,
        )
        Spacer(
            modifier = Modifier.height(10.dp),
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
    }
}

@Composable
internal fun Filter(
    onFilterClicked: () -> Unit,
) {

    val keyword by remember { mutableStateOf("") }

    val onKeywordChanged = { _: String ->

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.weight(0.9f),
        ) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                onValueChanged = onKeywordChanged,
                value = keyword,
                hint = stringResource(id = R.string.search_recruitment_filter_hint),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        JobisMediumIconButton(
            drawable = R.drawable.ic_filter,
            color = JobisButtonColor.MainSolidColor,
            onClick = onFilterClicked,
            shape = RoundedCornerShape(
                size = 4.dp,
            )
        )
    }
}

@Composable
private fun Recruitments(
    recruitmentUiModels: List<RecruitmentUiModel>,
    recruitmentViewModel: RecruitmentViewModel,
    bookmarkViewModel: BookmarkViewModel,
    navController: NavController,
) {

    val lazyListState = rememberLazyListState()

    var page by remember { mutableStateOf(1) }

    val lastIndex = remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

    LaunchedEffect(lastIndex.value) {
        if (recruitmentUiModels.size - 1 == lastIndex.value) {
            page += 1
            recruitmentViewModel.setPage(page + 1)
            recruitmentViewModel.fetchRecruitments()
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(
            bottom = 16.dp,
        ),
        state = lazyListState,
    ) {

        items(recruitmentUiModels) { recruitment ->

            val position = recruitment.jobCodeList.replace(',', '/')
            val trainPay = DecimalFormat("#,###").format(recruitment.trainPay)

            var isBookmarked by remember { mutableStateOf(recruitment.bookmarked) }

            Spacer(
                modifier = Modifier.height(16.dp),
            )
            Recruitment(
                imageUrl = recruitment.companyProfileUrl,
                position = position,
                isBookmarked = isBookmarked,
                companyName = recruitment.companyName,
                trainPay = stringResource(id = R.string.search_recruitment_train_pay, trainPay),
                isMilitarySupported = recruitment.military,
                onBookmarked = {
                    recruitmentUiModels[recruitmentUiModels.indexOf(recruitment)].bookmarked =
                        !recruitmentUiModels[recruitmentUiModels.indexOf(recruitment)].bookmarked
                    isBookmarked = !isBookmarked
                    bookmarkViewModel.bookmarkRecruitment(recruitment.recruitId.toLong())
                },
                onItemClicked = {
                    navController.currentBackStackEntry?.arguments?.putString(
                        "companyName",
                        recruitment.companyName
                    )
                    navController.navigate("RecruitmentDetails/${recruitment.recruitId}")
                }
            )
        }
    }
}

@Composable
private fun Recruitment(
    imageUrl: String,
    position: String,
    isBookmarked: Boolean,
    companyName: String,
    trainPay: String,
    isMilitarySupported: Boolean,
    onBookmarked: () -> Unit,
    onItemClicked: () -> Unit,
) {

    var isItemClicked by remember {
        mutableStateOf(false)
    }

    var bookmarked = isBookmarked

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = ApplyCompaniesItemShape)
            .background(color = JobisColor.Gray100)
            .jobisClickable(onClick = onItemClicked),
    ) {
        Row(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 20.dp,
                top = 8.dp,
                bottom = 8.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(90.dp),
                contentColor = JobisColor.Gray400,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .skeleton(show = imageUrl.isEmpty()),
                    model = imageUrl,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Body2(
                        text = position,
                    )
                    JobisImage(
                        modifier = Modifier.size(18.dp),
                        drawable = if (bookmarked) R.drawable.ic_bookmarked_on
                        else R.drawable.ic_bookmarked_off,
                        onClick = {
                            onBookmarked()
                            isItemClicked = !isItemClicked
                            bookmarked = !bookmarked
                        }
                    )
                }
                Caption(
                    text = companyName,
                    color = JobisColor.Gray600,
                )
                Spacer(
                    modifier = Modifier.height(8.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Caption(
                        text = trainPay,
                    )
                    if (isMilitarySupported) {
                        JobisImage(
                            modifier = Modifier.size(18.dp),
                            drawable = R.drawable.ic_military_support,
                        )
                    }
                }
            }
        }
    }
}