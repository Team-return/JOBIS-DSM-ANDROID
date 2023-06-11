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
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.launch
import team.retum.jobis_android.contract.RecruitmentEvent
import team.retum.jobis_android.contract.RecruitmentSideEffect
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobis_android.viewmodel.recruitment.Recruitment
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentViewModel
import team.retum.jobis_android.viewmodel.recruitment.toModel
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
) {

    val recruitments = remember { mutableStateListOf<Recruitment>() }

    LaunchedEffect(Unit) {
        recruitmentViewModel.sendEvent(
            event = RecruitmentEvent.FetchRecruitments(
                page = 1,
                code = null,
                company = null,
            )
        )
        recruitmentViewModel.container.sideEffectFlow.collect { it ->
            when (it) {
                is RecruitmentSideEffect.SuccessFetchRecruitmentsSideEffect -> {
                    recruitments.addAll(it.recruitmentsEntity.recruitmentEntities.map { it.toModel() })
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
            RecruitmentFilter()
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
                    top = 28.dp,
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
                    sheetState.showExpand()
                    sheetState.show()
                }
            }
            Recruitments(
                recruitments = recruitments,
                recruitmentViewModel = recruitmentViewModel,
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
        modifier = Modifier.fillMaxWidth(),
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
    recruitments: List<Recruitment>,
    recruitmentViewModel: RecruitmentViewModel,
    navController: NavController,
) {

    var page by remember { mutableStateOf(1) }

    val lazyListState = rememberLazyListState()

    val lastIndex = remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

    LaunchedEffect(lastIndex.value) {
        if (recruitments.size - 1 == lastIndex.value && recruitments.size % page == 0) {
            page += 1
            recruitmentViewModel.sendEvent(
                event = RecruitmentEvent.FetchRecruitments(
                    page = page,
                    code = null,
                    company = null,
                )
            )
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(
            bottom = 16.dp,
        ),
        state = lazyListState,
    ) {

        items(recruitments) { recruitment ->

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
                    isBookmarked = !isBookmarked
                    recruitmentViewModel.sendEvent(
                        event = RecruitmentEvent.BookmarkRecruitment(
                            recruitmentId = recruitment.recruitId.toLong()
                        )
                    )
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
            .shadow(
                shape = ApplyCompaniesItemShape,
                elevation = 8.dp,
            )
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
            )
        ) {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = imageUrl,
                contentDescription = null,
            )
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

@OptIn(ExperimentalMaterialApi::class)
internal suspend fun ModalBottomSheetState.showExpand() {
    animateTo(ModalBottomSheetValue.Expanded)
}