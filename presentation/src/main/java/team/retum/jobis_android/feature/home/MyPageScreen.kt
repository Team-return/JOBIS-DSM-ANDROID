package team.retum.jobis_android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.runBlocking
import team.retum.domain.entity.student.Department
import team.retum.jobis_android.contract.HomeSideEffect
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.home.HomeViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading6
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun MyPageScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    showDialog: () -> Unit,
) {

    val state = homeViewModel.container.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.container.sideEffectFlow.collect {
            when (it) {
                is HomeSideEffect.SuccessSignOut -> {
                    navController.navigate(JobisRoute.SignIn) {
                        popUpTo(JobisRoute.Main) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val studentInformation = state.value.studentInformation

    var showSignOutDialog by remember { mutableStateOf(false) }

    val onSignOutMainBtnClick = {
        homeViewModel.signOut()
    }

    val onSignOutSubBtnClick = {
        showSignOutDialog = false
    }

    val onBugReportClicked = {
        navController.navigate(JobisRoute.MainNavigation.BugReport)
    }

    val onInterestClicked = {

    }

    val onChangePasswordClicked = {
        navController.navigate(JobisRoute.ComparePassword)
    }

    val onSignOutClicked = {
        showSignOutDialog = true
    }

    if (showSignOutDialog) {
        Dialog(onDismissRequest = { showSignOutDialog = false }) {
            SignOutDialog(
                title = stringResource(id = R.string.sign_out_dialog_title),
                mainBtnText = stringResource(id = R.string.check),
                subBtnText = stringResource(id = R.string.cancel),
                onMainBtnClick = onSignOutMainBtnClick,
                onSubBtnClick = onSignOutSubBtnClick,
            )
        }
    }

    runBlocking {
        homeViewModel.fetchStudentInformations()
    }

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
        Header(text = stringResource(id = R.string.bottom_nav_my_page))
        Spacer(modifier = Modifier.height(50.dp))
        UserProfile(
            profileImageUrl = studentInformation.profileImageUrl,
            name = studentInformation.studentName,
            department = studentInformation.department,
            studentGcn = studentInformation.studentGcn,
            navController = navController,
            showDialog = showDialog,
        )
        Spacer(modifier = Modifier.height(80.dp))
        UserMenu(
            onBugReportClicked = onBugReportClicked,
            onInterestClicked = onInterestClicked,
            onChangePasswordClicked = onChangePasswordClicked,
            onSignOutClicked = onSignOutClicked
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UserProfile(
    profileImageUrl: String,
    name: String,
    department: Department,
    studentGcn: String,
    navController: NavController,
    showDialog: () -> Unit,
) {

    var grade = ""
    var classRoom = ""
    var number = ""

    val onProfileImageClicked = {

    }

    if (studentGcn.isNotEmpty()) {
        grade = studentGcn[0].toString().ifEmpty { "" }
        classRoom = studentGcn[1].toString().ifEmpty { "" }
        number = studentGcn.substring((if (studentGcn[2].toInt() == 0) 3 else 2)..3)
    }

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .jobisClickable(
                        rippleEnabled = true,
                        onClick = onProfileImageClicked,
                    )
                    .skeleton(
                        show = profileImageUrl.isEmpty(),
                        shape = CircleShape,
                    ),
                model = profileImageUrl,
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .skeleton(
                        show = profileImageUrl.isEmpty(),
                        shape = CircleShape,
                    )
                    .background(JobisColor.LightBlue),
                contentAlignment = Alignment.Center,
            ) {
                JobisImage(
                    drawable = R.drawable.ic_edit,
                )
            }
        }
        Spacer(modifier = Modifier.width(22.dp))
        Column {
            Heading6(
                modifier = Modifier
                    .defaultMinSize(minWidth = 56.dp)
                    .skeleton(show = name.isEmpty()),
                text = name,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Body2(
                modifier = Modifier
                    .defaultMinSize(minWidth = 164.dp)
                    .skeleton(show = department == Department.DEFAULT),
                text = department.department,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Body4(
                modifier = Modifier
                    .defaultMinSize(minWidth = 82.dp)
                    .skeleton(
                        show = grade.isEmpty()
                    ),
                text = if (grade.isNotEmpty()) stringResource(
                    id = R.string.student_gcn,
                    grade,
                    classRoom,
                    number
                )
                else "",
                color = JobisColor.Gray700,
            )
        }
    }
}

@Composable
private fun UserMenu(
    onBugReportClicked: () -> Unit,
    onInterestClicked: () -> Unit,
    onChangePasswordClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = JobisColor.Gray400,
    )
    Menu(
        content = stringResource(id = R.string.bug_report),
        onClick = onBugReportClicked,
    )
    Menu(
        content = stringResource(id = R.string.choose_interests),
        onClick = onInterestClicked,
    )
    Menu(
        content = stringResource(id = R.string.change_password),
        onClick = onChangePasswordClicked,
    )
    Menu(
        content = stringResource(id = R.string.log_out),
        contentColor = JobisColor.Red,
        onClick = onSignOutClicked,
    )
}

@Composable
private fun Menu(
    content: String,
    contentColor: Color = JobisColor.LightBlue,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .jobisClickable(rippleEnabled = true) {
                onClick()
            }
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Body4(
            text = content,
            color = contentColor,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
    }
}

// TODO move to design system
@Composable
private fun SignOutDialog(
    title: String,
    mainBtnText: String,
    subBtnText: String,
    onMainBtnClick: () -> Unit,
    onSubBtnClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(JobisColor.Gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Body2(
            text = title,
            color = JobisColor.Red,
        )
        Spacer(modifier = Modifier.height(26.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = 12.dp),
            color = JobisColor.Gray400,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .jobisClickable(
                        onClick = onSubBtnClick,
                        rippleEnabled = true
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body4(
                    text = subBtnText,
                    color = JobisColor.Gray600,
                )
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .padding(vertical = 4.dp),
            )
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .jobisClickable(
                        onClick = onMainBtnClick,
                        rippleEnabled = true,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body4(
                    text = mainBtnText,
                    color = JobisColor.Gray800,
                )
            }
        }
    }
}