package team.retum.jobis_android.feature.recruitment

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.recruitment.AreasEntity
import team.retum.domain.entity.recruitment.HiringProgress
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.Animated
import team.returm.jobisdesignsystem.util.jobisClickable

@Stable
val PositionCardShape = RoundedCornerShape(
    size = 4.dp,
)

@Composable
internal fun RecruitmentDetailsScreen(
    navController: NavController,
    recruitmentId: Long,
    recruitmentViewModel: RecruitmentViewModel = hiltViewModel(),
) {

    val state = recruitmentViewModel.container.stateFlow.collectAsState()

    val details = state.value.details

    val areas = state.value.details.areas

    LaunchedEffect(Unit) {
        recruitmentViewModel.setRecruitmentId(
            recruitmentId = recruitmentId,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = ScrollState(0),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CompanyInformation(
                companyName = details.companyName,
                companyProfileUrl = details.companyProfileUrl,
            ){
                navController.navigate("CompanyDetails/${details.companyId}/${true}")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = JobisColor.Gray400,
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecruitmentDetails(
                details = recruitmentViewModel.getRecruitmentDetails(),
                areas = areas,
                hiringProgress = details.hiringProgress,
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
        JobisLargeButton(
            text = stringResource(id = R.string.recruitment_details_do_apply),
            color = JobisButtonColor.MainSolidColor,
            onClick = {},
        )
    }
}

@Composable
private fun CompanyInformation(
    companyProfileUrl: String,
    companyName: String,
    onGetCompanyButtonClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = companyProfileUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Body1(text = companyName)
    }
    Spacer(modifier = Modifier.height(12.dp))
    JobisLargeButton(
        text = stringResource(id = R.string.recruitment_details_get_company),
        color = JobisButtonColor.MainGrayColor,
        onClick = onGetCompanyButtonClicked,
    )
}

@Composable
private fun RecruitmentDetails(
    details: List<Pair<Int, Any?>>,
    areas: List<AreasEntity>,
    hiringProgress: List<HiringProgress>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        RecruitmentDetail(
            title = stringResource(details[0].first),
            content = details[0].second.toString(),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Caption(
                modifier = Modifier.defaultMinSize(
                    minWidth = 68.dp,
                ),
                text = stringResource(id = R.string.recruitment_details_position),
                color = JobisColor.Gray700,
            )
            Spacer(modifier = Modifier.width(24.dp))
            areas.forEach {
                PositionCard(
                    position = it.job.replace(",", " / "),
                    workerCount = it.hiring.toString(),
                    majorTask = it.majorTask,
                    mainSkill = it.tech,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        repeat(details.size - 1) { index ->
            RecruitmentDetail(
                title = stringResource(details[index + 1].first),
                content = if (index == 6) {
                    StringBuilder().apply {
                        repeat(hiringProgress.size) { index ->
                            append("${index + 1}. ${hiringProgress[index].value}")
                            if (index != hiringProgress.lastIndex) append("\n")
                        }
                    }.toString()
                } else {
                    (details[index + 1].second
                        ?: stringResource(id = R.string.company_details_null)).toString()
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

@Composable
private fun RecruitmentDetail(
    title: String,
    content: String,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
    ) {
        Caption(
            modifier = Modifier.defaultMinSize(
                minWidth = 68.dp,
            ),
            text = title,
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.width(24.dp))
        Caption(
            text = content,
            color = JobisColor.Gray900,
        )
    }
}

@Composable
private fun PositionCard(
    position: String,
    workerCount: String,
    majorTask: String,
    mainSkill: List<String>,
) {

    var showDetails by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .defaultMinSize(minWidth = 200.dp)
            .clip(shape = PositionCardShape)
            .background(color = JobisColor.Gray100)
            .border(
                width = 1.dp,
                color = JobisColor.Gray400,
                shape = PositionCardShape,
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Body3(text = position)
            Caption(
                text = stringResource(id = R.string.recruitment_details_count, workerCount),
                color = JobisColor.Gray600,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Animated(
            visible = showDetails,
            isBounce = true,
        ) {
            Column {
                Caption(
                    text = stringResource(id = R.string.recruitment_details_main_work),
                    color = JobisColor.Gray600,
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Animated(
            visible = showDetails
        ) {
            Caption(text = majorTask)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Animated(
            visible = showDetails,
            isBounce = true,
        ) {
            Column {
                Caption(
                    text = stringResource(id = R.string.recruitment_details_main_skills),
                    color = JobisColor.Gray600,
                )
                Caption(
                    text = StringBuilder().apply {
                        mainSkill.forEach {
                            append(it)
                            if (mainSkill.indexOf(it) != mainSkill.lastIndex) {
                                append(", ")
                            }
                        }
                    }.toString()
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Caption(
                modifier = Modifier.jobisClickable {
                    showDetails = !showDetails
                },
                text = if (showDetails) stringResource(id = R.string.recruitment_details_show_simply)
                else stringResource(id = R.string.recruitment_details_show_detail),
                color = JobisColor.Gray600,
            )
        }
    }
}