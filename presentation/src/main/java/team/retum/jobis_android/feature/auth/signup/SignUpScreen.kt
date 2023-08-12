package team.retum.jobis_android.feature.auth.signup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.SignUpSideEffect
import team.retum.jobis_android.feature.auth.signup.setpassword.SetPasswordScreen
import team.retum.jobis_android.feature.auth.signup.studentinfo.StudentInfoScreen
import team.retum.jobis_android.feature.auth.signup.verifyemail.VerifyEmailScreen
import team.retum.jobis_android.root.JobisAppState
import team.retum.jobis_android.root.navigation.NavigationRoute
import team.retum.jobis_android.util.compose.component.TopBar
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading5
import team.returm.jobisdesignsystem.toast.ToastType

@Stable
const val maxProgress = 3

@Stable
private val titleList = listOf(
    R.string.input_personal_information,
    R.string.email_verification,
    R.string.set_password,
)

@Composable
internal fun SignUpScreen(
    appState: JobisAppState,
    signUpViewModel: SignUpViewModel,
    navigateToMain: () -> Unit,
    navigatePopBackStack: () -> Unit,
) {

    var currentProgress by remember { mutableStateOf(1) }

    val state by signUpViewModel.container.stateFlow.collectAsState()

    val isSuccessVerifyEmail by remember { mutableStateOf(false) }

    val navController = appState.navController

    val navigateToVerifyEmail = {
        navController.navigate(NavigationRoute.VerifyEmail)
    }

    val navigateToSetPassword = {
        navController.navigate(NavigationRoute.SetPassword)
    }

    val notFoundToastMessage = stringResource(id = R.string.student_info_not_found_student)
    val sendAuthCodeSuccessToastTitle =
        stringResource(id = R.string.email_verification_send_auth_code_success_title)
    val sendAuthCodeSuccessToastMessage =
        stringResource(id = R.string.email_verification_send_auth_code_success_message)
    val emailConflict = stringResource(id = R.string.email_verification_conflict)
    val signUpAccountConflict = stringResource(id = R.string.sign_up_account_conflict)

    val moveToBack = {
        when (currentProgress) {
            1 -> navigatePopBackStack()
            else -> navController.popBackStack()
        }
        currentProgress -= 1
    }

    BackHandler {
        moveToBack()
    }

    LaunchedEffect(Unit) {
        signUpViewModel.container.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.StudentInfo.CheckStudentExistsSuccess -> {
                    navigateToVerifyEmail()
                    currentProgress = 2
                }

                is SignUpSideEffect.StudentInfo.CheckStudentExistsNotFound -> {
                    appState.showToast(
                        message = notFoundToastMessage,
                        toastType = ToastType.Error,
                    )
                }

                is SignUpSideEffect.VerifyEmail.VerifyEmailSuccess -> {
                    navigateToSetPassword()
                    currentProgress = 3
                }

                is SignUpSideEffect.VerifyEmail.EmailConflict -> {
                    appState.showToast(
                        message = emailConflict,
                        toastType = ToastType.Error,
                    )
                }

                is SignUpSideEffect.VerifyEmail.SendAuthCodeSuccess -> {
                    appState.showToast(
                        title = sendAuthCodeSuccessToastTitle,
                        message = sendAuthCodeSuccessToastMessage,
                        toastType = ToastType.Success,
                    )
                }

                is SignUpSideEffect.SetPassword.SignUpConflict -> {
                    appState.showToast(
                        message = signUpAccountConflict,
                        toastType = ToastType.Error,
                    )
                }

                is SignUpSideEffect.SetPassword.SignUpSuccess -> {
                    navigateToMain()
                }

                is SignUpSideEffect.Exception -> {
                    appState.showToast(
                        message = it.message,
                        toastType = ToastType.Error,
                    )
                }
            }
        }
    }


    val progressAnimation by animateFloatAsState(
        targetValue = (currentProgress.toFloat() / maxProgress.toFloat()), animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ), label = ""
    )

    val onTopBarClicked = {
        moveToBack()
    }

    val onNextButtonClicked = {
        when (currentProgress) {
            1 -> signUpViewModel.checkStudentExists()
            2 -> signUpViewModel.verifyEmail()
            3 -> signUpViewModel.signUp()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(56.dp))
            SignUpHeader(
                currentProgress = currentProgress,
                onTopBarClicked = onTopBarClicked,
            )
            Spacer(modifier = Modifier.height(50.dp))
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.StudentInfo,
            ) {
                composable(route = NavigationRoute.StudentInfo) {
                    StudentInfoScreen(signUpViewModel = signUpViewModel)
                }
                composable(route = NavigationRoute.VerifyEmail) {
                    VerifyEmailScreen(signUpViewModel = signUpViewModel)
                }
                composable(route = NavigationRoute.SetPassword) {
                    SetPasswordScreen(signUpViewModel = signUpViewModel)
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            ProgressBarWithButton(
                currentProgress = currentProgress,
                progress = progressAnimation,
                buttonEnabled = state.signUpButtonEnabled,
                isSuccessVerifyEmail = isSuccessVerifyEmail,
                onClick = onNextButtonClicked,
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ProgressBarWithButton(
    currentProgress: Int,
    progress: Float,
    buttonEnabled: Boolean,
    isSuccessVerifyEmail: Boolean,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Caption(text = "$currentProgress / $maxProgress")
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = progress,
            backgroundColor = JobisColor.Gray500,
            color = JobisColor.LightBlue,
        )
        Spacer(modifier = Modifier.height(20.dp))
        JobisLargeButton(
            text = stringResource(
                id = if (currentProgress == 2 && !isSuccessVerifyEmail) R.string.verification
                else R.string.next,
            ),
            color = JobisButtonColor.MainSolidColor,
            enabled = buttonEnabled,
            onClick = onClick,
        )
    }
}

@Composable
private fun SignUpHeader(
    currentProgress: Int,
    onTopBarClicked: () -> Unit,
) {
    TopBar(
        text = stringResource(id = R.string.sign_up),
        onClick = onTopBarClicked,
    )
    Spacer(modifier = Modifier.height(50.dp))
    Heading5(
        text = stringResource(
            id = if (currentProgress in 1..3) titleList[currentProgress - 1]
            else titleList.first()
        ),
    )
}
