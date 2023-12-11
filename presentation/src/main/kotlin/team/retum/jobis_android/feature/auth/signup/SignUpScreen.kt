package team.retum.jobis_android.feature.auth.signup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.feature.auth.signup.setpassword.SetPasswordScreen
import team.retum.jobis_android.feature.auth.signup.studentinfo.StudentInfoScreen
import team.retum.jobis_android.feature.auth.signup.verifyemail.VerifyEmailScreen
import team.retum.jobis_android.navigation.AuthDestinations
import team.retum.jobis_android.navigation.navigateToSetPassword
import team.retum.jobis_android.navigation.navigateToVerifyEmail
import team.retum.jobis_android.util.compose.component.TopBar
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading5

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
    signUpViewModel: SignUpViewModel,
    navigateToMain: () -> Unit,
    navigatePopBackStack: () -> Unit,
) {
    val appState = LocalAppState.current

    val context = LocalContext.current

    var currentProgress by remember { mutableStateOf(1) }

    val state by signUpViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val isSuccessVerifyEmail by remember { mutableStateOf(false) }

    val navController = rememberNavController()

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
                    navController.navigateToVerifyEmail()
                    currentProgress = 2
                }

                is SignUpSideEffect.StudentInfo.CheckStudentExistsNotFound -> {
                    appState.showErrorToast(message = context.getString(R.string.student_info_not_found_student))
                }

                is SignUpSideEffect.VerifyEmail.VerifyEmailSuccess -> {
                    navController.navigateToSetPassword()
                    currentProgress = 3
                }

                is SignUpSideEffect.VerifyEmail.EmailConflict -> {
                    appState.showErrorToast(message = context.getString(R.string.email_verification_conflict))
                }

                is SignUpSideEffect.VerifyEmail.SendAuthCodeSuccess -> {
                    appState.showSuccessToast(
                        title = context.getString(R.string.email_verification_send_auth_code_success_title),
                        message = context.getString(R.string.email_verification_send_auth_code_success_message),
                    )
                }

                is SignUpSideEffect.SetPassword.SignUpConflict -> {
                    appState.showErrorToast(message = context.getString(R.string.sign_up_account_conflict))
                }

                is SignUpSideEffect.SetPassword.SignUpSuccess -> {
                    navigateToMain()
                }

                is SignUpSideEffect.Exception -> {
                    appState.showErrorToast(message = it.message)
                }
            }
        }
    }

    val progressAnimation by animateFloatAsState(
        targetValue = (currentProgress.toFloat() / maxProgress.toFloat()),
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "",
    )

    val onNextButtonClicked: () -> Unit = {
        when (currentProgress) {
            1 -> signUpViewModel.checkStudentExists()
            2 -> signUpViewModel.verifyEmail()
            else -> signUpViewModel.signUp()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        SignUpHeader(
            currentProgress = currentProgress,
            onTopBarClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(50.dp))
        NavHost(
            navController = navController,
            startDestination = AuthDestinations.StudentInfo,
        ) {
            composable(route = AuthDestinations.StudentInfo) {
                StudentInfoScreen(signUpViewModel = signUpViewModel)
            }
            composable(route = AuthDestinations.VerifyEmail) {
                VerifyEmailScreen(signUpViewModel = signUpViewModel)
            }
            composable(route = AuthDestinations.SetPassword) {
                SetPasswordScreen(signUpViewModel = signUpViewModel)
            }
        }
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

@Composable
private fun ProgressBarWithButton(
    currentProgress: Int,
    progress: Float,
    buttonEnabled: Boolean,
    isSuccessVerifyEmail: Boolean,
    onClick: () -> Unit,
) {
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
            id = if (currentProgress == 2 && !isSuccessVerifyEmail) {
                R.string.verification
            } else {
                R.string.next
            },
        ),
        color = JobisButtonColor.MainSolidColor,
        enabled = buttonEnabled,
        onClick = onClick,
    )
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
            id = if (currentProgress in 1..3) {
                titleList[currentProgress - 1]
            } else {
                titleList.first()
            },
        ),
    )
}
