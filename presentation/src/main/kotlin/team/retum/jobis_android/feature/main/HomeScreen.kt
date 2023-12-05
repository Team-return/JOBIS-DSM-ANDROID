package team.retum.jobis_android.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.applications.AppliedCompanyEntity
import team.retum.domain.enums.Department
import team.retum.jobis_android.feature.application.RecruitmentApplicationDialog
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.viewmodel.home.HomeViewModel
import team.retum.jobis_android.viewmodel.mypage.MyPageViewModel
import team.returm.jobisdesignsystem.colors.JobisColor
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
    size = 16.dp,
)

@Stable
private val recruitmentStatusShape = RoundedCornerShape(
    bottomStart = 34.dp,
    bottomEnd = 34.dp,
)

@Composable
internal fun HomeScreen(
    navigateToMyPage: () -> Unit,
    navigateToRecruitments: (isWinterIntern: Boolean) -> Unit,
    navigateToCompanies: () -> Unit,
    navigateToNotifications: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {
    val homeState by homeViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val myPageState by myPageViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val studentCounts = homeState.studentCounts

    var showApplicationDialog by remember { mutableStateOf(false) }

    var applicationId: Long? by remember { mutableStateOf(null) }

    if (showApplicationDialog) {
        Dialog(onDismissRequest = { showApplicationDialog = false }) {
            RecruitmentApplicationDialog(
                isReApply = true,
                recruitmentId = applicationId ?: 0L,
            ) {
                showApplicationDialog = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JobisColor.Gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RecruitmentStatus(
            totalStudentCount = studentCounts.totalStudentCount,
            passCount = studentCounts.passCount,
            approvedCount = studentCounts.approvedCount,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                UserInformation(
                    profileImageUrl = myPageState.profileImageUrl,
                    name = myPageState.studentName,
                    gcn = myPageState.studentGcn,
                    department = myPageState.department,
                    navigateToMyPage = navigateToMyPage,
                    navigateToNotifications = navigateToNotifications,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Caption(
                    text = stringResource(id = R.string.home_apply_status),
                    color = JobisColor.Gray600,
                )
                Spacer(modifier = Modifier.height(6.dp))
                ApplyCompanies(
                    appliedCompanies = homeState.appliedCompanyHistories,
                    showApplicationDialog = {
                        applicationId = it
                        showApplicationDialog = true
                    },
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(JobisColor.Gray300),
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
                    menus = listOf(
                        Menu(
                            stringRes = R.string.home_do_get_recruitment,
                            drawableRes = R.drawable.ic_get_recruitment,
                            onClick = { navigateToRecruitments(false) },
                        ),
                        Menu(
                            stringRes = R.string.home_do_get_company,
                            drawableRes = R.drawable.ic_get_company,
                            onClick = navigateToCompanies,
                        ),
                        Menu(
                            stringRes = R.string.home_do_get_winter_intern,
                            drawableRes = R.drawable.ic_gift,
                            onClick = { navigateToRecruitments(true) },
                        ),
                    ),
                )
                Spacer(modifier = Modifier.height(48.dp))
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

    if (passCount != 0L) {
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
                        JobisColor.Blue,
                        JobisColor.LightBlue,
                    ),
                ),
            )
            .padding(
                top = 20.dp,
                start = 32.dp,
                end = 32.dp,
                bottom = 20.dp,
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Row(verticalAlignment = Alignment.Bottom) {
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
        Row(verticalAlignment = Alignment.Bottom) {
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

@Composable
private fun UserInformation(
    profileImageUrl: String,
    name: String,
    gcn: String,
    department: Department,
    navigateToMyPage: () -> Unit,
    navigateToNotifications: () -> Unit,
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
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(verticalArrangement = Arrangement.SpaceBetween) {
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
        // TODO fcm 구현 시 작업
        /*Image(
            modifier = Modifier
                .size(24.dp)
                .jobisClickable(onClick = navigateToNotifications),
            painter = painterResource(R.drawable.ic_notification),
            contentDescription = null,
        )*/
    }
}

@Composable
private fun ApplyCompanies(
    appliedCompanies: List<AppliedCompanyEntity>,
    showApplicationDialog: ((recruitmentId: Long?) -> Unit)?,
) {
    if (appliedCompanies.isNotEmpty()) {
        Column(
            modifier = Modifier
                .height(220.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(appliedCompanies.size) { index ->
                ApplyCompany(
                    applicationId = appliedCompanies[index].applicationId,
                    company = appliedCompanies[index].company,
                    status = appliedCompanies[index].applicationStatus.status,
                    showApplicationDialog = showApplicationDialog,
                )
            }
        }
    } else {
        ApplyCompany(isEmpty = true)
    }
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
private fun ApplyCompany(
    applicationId: Long? = null,
    company: String? = null,
    status: String? = null,
    isEmpty: Boolean? = null,
    showApplicationDialog: ((recruitmentId: Long?) -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(shape = ApplyCompaniesItemShape)
            .jobisClickable(
                rippleEnabled = true,
                onClick = { showApplicationDialog?.invoke(applicationId) },
                enabled = applicationId != null,
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
                modifier = Modifier.padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Spacer(modifier = Modifier.height(10.dp))
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
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

data class Menu(
    @StringRes val stringRes: Int,
    @DrawableRes val drawableRes: Int,
    val onClick: () -> Unit,
)

@Composable
private fun MenuCardGroup(
    menus: List<Menu>,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        items(menus) {
            MenuCard(
                text = stringResource(id = it.stringRes),
                drawable = painterResource(id = it.drawableRes),
                onClick = it.onClick,
            )
        }
    }
}

@Composable
private fun MenuCard(
    text: String,
    drawable: Painter,
    onClick: () -> Unit,
) {
    val interactionSource = MutableInteractionSource()

    Column(
        modifier = Modifier
            .clip(shape = JobisSize.Shape.Large)
            .height(180.dp)
            .background(color = JobisColor.Gray100)
            .jobisClickable(
                rippleEnabled = true,
                interactionSource = interactionSource,
                onClick = onClick,
            )
            .padding(
                vertical = 16.dp,
                horizontal = 22.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Body1(text = text)
        Image(
            painter = drawable,
            contentDescription = null,
        )
    }
}
