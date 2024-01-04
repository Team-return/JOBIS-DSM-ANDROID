package team.retum.jobis_android.feature.recruitment

import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.domain.entity.recruitment.AreasEntity
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.jobis_android.feature.application.RecruitmentApplicationDialog
import team.retum.jobis_android.feature.company.getNavigationRoute
import team.retum.jobis_android.navigation.MainDestinations
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.Animated
import team.returm.jobisdesignsystem.util.jobisClickable
import java.text.DecimalFormat

@Stable
val PositionCardShape = RoundedCornerShape(4.dp)

@Composable
internal fun RecruitmentDetailsScreen(
    recruitmentId: Long?,
    getPreviousDestination: () -> String?,
    navigateToCompanyDetails: (Long) -> Unit,
    recruitmentViewModel: RecruitmentViewModel = hiltViewModel(),
) {
    val state by recruitmentViewModel.collectAsState()
    val details = state.details
    val areas = state.details.areas
    var companyDetailsButtonVisibility by remember { mutableStateOf(true) }
    var applicationDialogState by remember { mutableStateOf(false) }
    val onApplyButtonClicked = {
        applicationDialogState = true
    }

    LaunchedEffect(Unit) {
        companyDetailsButtonVisibility =
            getPreviousDestination()?.getNavigationRoute() != MainDestinations.CompanyDetails.getNavigationRoute()

        if (recruitmentId != null) {
            recruitmentViewModel.setRecruitmentId(recruitmentId)
        }
    }

    if (applicationDialogState) {
        Dialog(
            onDismissRequest = { applicationDialogState = false },
            properties = DialogProperties(usePlatformDefaultWidth = true),
        ) {
            RecruitmentApplicationDialog(recruitmentId = recruitmentId ?: 0) {
                applicationDialogState = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .jobisClickable { applicationDialogState = false },
        contentAlignment = Alignment.BottomCenter,
    ) {
        with(details) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CompanyInformation(
                    modifier = Modifier.padding(
                        top = 48.dp,
                        bottom = 12.dp,
                    ),
                    companyName = companyName,
                    companyProfileUrl = companyProfileUrl,
                )
                if (companyDetailsButtonVisibility) {
                    JobisLargeButton(
                        text = stringResource(id = R.string.recruitment_details_get_company),
                        color = JobisButtonColor.MainGrayColor,
                        onClick = { navigateToCompanyDetails(companyId) },
                        enabled = companyName.isNotEmpty(),
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp,
                            bottom = 20.dp,
                        ),
                    color = JobisColor.Gray400,
                )
                RecruitmentDetails(
                    modifier = Modifier.padding(bottom = 80.dp),
                    details = this@with,
                    areas = areas,
                )
            }
            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                JobisLargeButton(
                    text = stringResource(id = R.string.recruitment_details_do_apply),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = onApplyButtonClicked,
                    enabled = companyId != 0L,
                )
            }
        }
    }
}

@Composable
internal fun CompanyInformation(
    modifier: Modifier = Modifier,
    companyProfileUrl: String,
    companyName: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .skeleton(show = companyProfileUrl.isBlank()),
            model = companyProfileUrl,
            contentDescription = stringResource(id = R.string.company_details_company_profile),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Body1(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .skeleton(companyName.isBlank()),
            text = companyName,
        )
    }
}

@Composable
private fun RecruitmentDetails(
    modifier: Modifier = Modifier,
    details: RecruitmentDetailsEntity,
    areas: List<AreasEntity>,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        with(details) {
            Detail(
                title = stringResource(R.string.recruitment_details_period),
                content = "$startDate${if (startDate.isNotBlank()) '~' else ""}$endDate",
            )
            Positions(areas = areas)
            Spacer(modifier = Modifier.height(10.dp))
            if (!requiredLicenses.isNullOrEmpty()) {
                Detail(
                    title = stringResource(id = R.string.recruitment_details_licenses),
                    content = StringBuilder().apply {
                        requiredLicenses?.forEach {
                            append("$it ")
                        }
                    }.toString().trim().replace(" ", ", "),
                )
            }
            if (requiredGrade != null) {
                Detail(
                    title = stringResource(id = R.string.recruitment_details_required_grade),
                    content = "$requiredGrade%",
                )
            }
            Detail(
                title = stringResource(id = R.string.recruitment_details_worker_time),
                content = "$startTime ${if (startTime.isNotBlank()) "~" else ""} $endTime",
            )
            Detail(
                title = stringResource(id = R.string.recruitment_details_train_pay),
                content = if (trainPay != 0L) "${DecimalFormat("#,###").format(trainPay)}원/월" else "",
            )
            if (!pay.isNullOrBlank()) {
                Detail(
                    title = stringResource(id = R.string.recruitment_details_pay),
                    content = "${pay}만 원/년",
                )
            }
            if (!benefits.isNullOrEmpty()) {
                Detail(
                    title = stringResource(id = R.string.recruitment_details_benefits),
                    content = "$benefits",
                )
            }
            Detail(
                title = stringResource(id = R.string.recruitment_details_hiring_progress),
                content = StringBuilder().apply {
                    hiringProgress.forEachIndexed { index, it ->
                        append("${index + 1}.${it.value} ")
                        if (index != hiringProgress.lastIndex) {
                            append("\n")
                        }
                    }
                }.toString(),
            )
            Detail(
                title = stringResource(id = R.string.recruitment_details_required_documents),
                content = submitDocument,
            )
            if (!etc.isNullOrEmpty()) {
                Detail(
                    title = stringResource(id = R.string.recruitment_details_etc),
                    content = etc ?: "",
                )
            }
        }
    }
}

@Composable
internal fun Detail(
    title: String,
    content: String,
) {
    Row(horizontalArrangement = Arrangement.Start) {
        Caption(
            modifier = Modifier.defaultMinSize(minWidth = 68.dp),
            text = title,
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.width(24.dp))
        Caption(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .skeleton(content.isBlank() || content == "0"),
            text = content,
            color = JobisColor.Gray900,
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun Positions(
    areas: List<AreasEntity>,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        Caption(
            modifier = Modifier.defaultMinSize(minWidth = 68.dp),
            text = stringResource(id = R.string.recruitment_details_position),
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.width(24.dp))
        if (areas.isNotEmpty()) {
            Column {
                areas.forEach {
                    PositionCard(
                        position = it.job.toString().replace("[", " ").replace("]", " ").trim(),
                        workerCount = it.hiring.toString(),
                        majorTask = it.majorTask,
                        mainSkill = it.tech,
                        preferentialTreatment = it.preferentialTreatment,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .skeleton(areas.isEmpty()),
            )
        }
    }
}

@Composable
private fun PositionCard(
    position: String,
    workerCount: String,
    majorTask: String,
    mainSkill: List<String>,
    preferentialTreatment: String?,
) {
    var showDetails by remember { mutableStateOf(false) }

    val maxLines by animateIntAsState(
        targetValue = if (showDetails) {
            100
        } else {
            1
        },
        label = "",
    )

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
            Body3(
                modifier = Modifier.weight(1f),
                text = position,
            )
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
            Caption(
                text = stringResource(id = R.string.recruitment_details_main_work),
                color = JobisColor.Gray600,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Caption(
            text = majorTask,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
        )
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
                    }.toString(),
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (preferentialTreatment != null) {
                    Caption(
                        text = stringResource(id = R.string.recruitment_details_preferential_treatment),
                        color = JobisColor.Gray600,
                    )
                    Caption(text = preferentialTreatment)
                }
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
                text = if (showDetails) {
                    stringResource(id = R.string.recruitment_details_show_simply)
                } else {
                    stringResource(id = R.string.recruitment_details_show_detail)
                },
                color = JobisColor.Gray600,
            )
        }
    }
}
