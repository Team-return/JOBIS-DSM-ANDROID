package team.retum.jobis_android.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.applications.AppliedCompanyEntity
import team.retum.domain.entity.student.Department
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.viewmodel.home.HomeViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading3
import team.returm.jobisdesignsystem.util.JobisSize
import team.returm.jobisdesignsystem.util.jobisClickable
import java.text.DecimalFormat

@Stable
val ApplyCompaniesItemShape = RoundedCornerShape(
    size = 10.dp,
)

@Stable
private val recruitmentStatusShape = RoundedCornerShape(
    bottomStart = 34.dp,
    bottomEnd = 34.dp,
)

@Composable
internal fun HomeScreen(
    navController: NavController,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val state by homeViewModel.container.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        with(homeViewModel) {
            fetchTotalPassedStudentCount()
            fetchStudentInformations()
            fetchAppliedCompanyHistories()
        }
    }

    val studentCounts = state.studentCounts
    val studentInformation = state.studentInformation

    val onRecruitmentsMenuClicked = {
        navController.navigate(JobisRoute.Recruitments)
    }

    val onCompaniesMenuClicked = {
        navController.navigate(JobisRoute.Companies)
    }

    val navigateToMyPage = {
        navHostController.navigate(JobisRoute.Navigation.MyPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JobisColor.Gray100)
            .padding(bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RecruitmentStatus(
            totalStudentCount = studentCounts.totalStudentCount,
            passCount = studentCounts.passCount,
            approvedCount = studentCounts.approvedCount,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
            ) {
                UserInformation(
                    profileImageUrl = studentInformation.profileImageUrl,
                    name = studentInformation.studentName,
                    gcn = studentInformation.studentGcn,
                    department = studentInformation.department,
                    navigateToMyPage = navigateToMyPage,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Caption(
                    text = stringResource(id = R.string.home_apply_status),
                    color = JobisColor.Gray600,
                )
                Spacer(modifier = Modifier.height(6.dp))
                ApplyCompanies(appliedCompanies = state.appliedCompanyHistories)
            }

            Column(
                modifier = Modifier.background(JobisColor.Gray300),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = JobisColor.Gray400,
                )
                Spacer(modifier = Modifier.height(26.dp))
                MenuCardGroup(
                    onRecruitmentsMenuClicked = onRecruitmentsMenuClicked,
                    onCompaniesMenuClicked = onCompaniesMenuClicked,
                )
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
private fun RecruitmentStatus(
    totalStudentCount: Long,
    passCount: Long,
    approvedCount: Long,
) {

    var employmentRate = 0f
    val passString = "$passCount / $totalStudentCount"
    val appliedString = "$approvedCount / $totalStudentCount"

    if (approvedCount != 0L) {
        employmentRate =
            DecimalFormat("##0.0").format(passCount.toFloat() / totalStudentCount.toFloat() * 100)
                .toFloat()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                shape = recruitmentStatusShape,
                elevation = 4.dp,
            )
            .clip(shape = recruitmentStatusShape)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        JobisColor.Blue, JobisColor.LightBlue
                    )
                )
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.padding(
                top = 20.dp,
                start = 32.dp,
                end = 32.dp,
                bottom = 20.dp,
            ),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Heading3(
                        text = employmentRate.toString(),
                        color = JobisColor.Gray100,
                    )
                    Body1(
                        modifier = Modifier.padding(
                            start = 4.dp,
                            bottom = 4.dp,
                        ),
                        text = "%",
                        color = JobisColor.Gray100,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Caption(
                    modifier = Modifier.padding(
                        bottom = 4.dp,
                    ),
                    text = "${stringResource(id = R.string.home_count_success_candidate)} : $passString",
                    color = JobisColor.Gray100,
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Body4(
                    text = stringResource(id = R.string.home_employment_rate),
                    color = JobisColor.Gray100,
                )
                Spacer(modifier = Modifier.weight(1f))
                Caption(
                    text = "${stringResource(id = R.string.home_count_apply_candidate)} : $appliedString",
                    color = JobisColor.Gray100,
                )
            }
        }
    }
}

@Composable
private fun UserInformation(
    profileImageUrl: String,
    name: String,
    gcn: String,
    department: Department,
    navigateToMyPage: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = JobisSize.Shape.Large)
            .border(
                width = 1.dp,
                color = JobisColor.Gray400,
                shape = JobisSize.Shape.Large,
            )
            .jobisClickable(
                rippleEnabled = true,
                onClick = navigateToMyPage,
            )
            .background(
                color = JobisColor.Gray100,
                shape = JobisSize.Shape.Large,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .skeleton(
                    show = profileImageUrl.isEmpty(),
                    shape = CircleShape,
                ),
            model = profileImageUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Body2(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .defaultMinSize(minWidth = 72.dp)
                    .skeleton(show = gcn.isEmpty()),
                text = "$gcn $name",
            )
            Caption(
                modifier = Modifier
                    .defaultMinSize(minWidth = 104.dp)
                    .skeleton(show = department.department.isEmpty()),
                text = department.department,
                color = JobisColor.Gray600,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        JobisImage(
            modifier = Modifier.size(24.dp),
            drawable = R.drawable.ic_notification,
        )
    }
}

@Composable
private fun ApplyCompanies(
    appliedCompanies: List<AppliedCompanyEntity>,
) {
    val size = if (appliedCompanies.size >= 2) 2
    else appliedCompanies.size

    if (appliedCompanies.isNotEmpty()) {
        Column {
            repeat(size) { index ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                ApplyCompany(
                    company = appliedCompanies[index].company,
                    status = appliedCompanies[index].applicationStatus.status,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    } else {
        ApplyCompany(isEmpty = true)
    }
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
private fun ApplyCompany(
    company: String? = null,
    status: String? = null,
    isEmpty: Boolean? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(
                shape = ApplyCompaniesItemShape,
            )
            .border(
                width = 1.dp,
                color = JobisColor.Gray400,
                shape = ApplyCompaniesItemShape,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isEmpty == true) {
            Caption(
                text = stringResource(id = R.string.home_no_apply_companies),
                color = JobisColor.Gray500,
            )
        } else {
            Column(
                modifier = Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 10.dp,
                ),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Body3(text = company!!)
                    Body3(
                        text = " $status ",
                        color = JobisColor.LightBlue,
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuCardGroup(
    onRecruitmentsMenuClicked: () -> Unit,
    onCompaniesMenuClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            MenuCard(
                text = stringResource(id = R.string.home_do_get_recruitment),
                drawable = R.drawable.ic_get_recruitment,
                onClick = onRecruitmentsMenuClicked,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            MenuCard(
                text = stringResource(id = R.string.home_do_get_company),
                drawable = R.drawable.ic_get_company,
                onClick = onCompaniesMenuClicked,
            )
        }
    }
}

@Composable
private fun MenuCard(
    text: String,
    @DrawableRes drawable: Int,
    onClick: () -> Unit,
) {

    val interactionSource = MutableInteractionSource()

    Column(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clip(shape = JobisSize.Shape.Large)
            .background(
                color = JobisColor.Gray100,
            )
            .jobisClickable(
                rippleEnabled = true,
                interactionSource = interactionSource,
                onClick = onClick,
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Body1(text = text)
        Image(
            painter = painterResource(id = drawable),
            contentDescription = null,
        )
    }
}