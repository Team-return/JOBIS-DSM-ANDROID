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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.SignUpEvent
import team.retum.jobis_android.feature.auth.signup.setpassword.SetPasswordScreen
import team.retum.jobis_android.feature.auth.signup.studentinfo.StudentInfoScreen
import team.retum.jobis_android.feature.auth.signup.verifyemail.VerifyEmailScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.TopBar
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading5

@Stable
const val maxProgress = 3

@Stable
val titleList = listOf(
    R.string.input_personal_information,
    R.string.email_verification,
    R.string.set_password,
)

@Composable
fun SignUpScreen(
    navHostController: NavController,
    signUpViewModel: SignUpViewModel,
    moveToSignIn: () -> Unit,
    moveToMain: () -> Unit,
) {

    var currentProgress by remember { mutableStateOf(0) }

    var buttonEnabled by remember { mutableStateOf(false) }

    val isSuccessVerifyEmail by remember { mutableStateOf(false) }

    val navController = rememberNavController()

    BackHandler {
        currentProgress = navigatePopBackStack(
            currentProgress = currentProgress,
            moveToSignIn = moveToSignIn,
        )
    }

    LaunchedEffect(currentProgress) {
        buttonEnabled = false
        when (currentProgress) {
            0 -> navController.navigate(JobisRoute.StudentInfo)
            1 -> navController.navigate(JobisRoute.VerifyEmail)
            2 -> navController.navigate(JobisRoute.SetPassword)
        }
    }


    val progressAnimation by animateFloatAsState(
        targetValue = (currentProgress.toFloat() / maxProgress.toFloat()),
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        )
    )

    val onTopBarClicked = {
        currentProgress = navigatePopBackStack(
            currentProgress = currentProgress,
            moveToSignIn = moveToSignIn,
        )
    }

    val onNextButtonClicked = {
        when (currentProgress) {
            0 -> {
                signUpViewModel.sendEvent(
                    event = SignUpEvent.CheckStudentExists,
                )
            }

            1 -> {
                signUpViewModel.sendEvent(
                    event = SignUpEvent.VerifyEmail,
                )
            }

            2 -> {
                signUpViewModel.sendEvent(
                    event = SignUpEvent.SignUp,
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 36.dp,
                start = 20.dp,
                end = 20.dp,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            AuthLayoutHeaders(
                currentProgress = currentProgress,
                titleList = titleList,
                onTopBarClicked = onTopBarClicked
            )
            Spacer(modifier = Modifier.height(50.dp))
            NavHost(
                navController = navController,
                startDestination = JobisRoute.StudentInfo,
            ) {
                composable(
                    route = JobisRoute.StudentInfo,
                ) {
                    StudentInfoScreen(
                        signUpViewModel = signUpViewModel,
                        navigate = { currentProgress++ },
                    ) {
                        buttonEnabled = it
                    }
                }

                composable(
                    route = JobisRoute.VerifyEmail,
                ) {
                    VerifyEmailScreen(
                        navController = navController,
                        signUpViewModel = signUpViewModel,
                        changeButtonState = {
                            println(it)
                            buttonEnabled = it
                        }
                    ) {
                        currentProgress++
                    }
                }

                composable(
                    route = JobisRoute.SetPassword,
                ) {
                    SetPasswordScreen(
                        navController = navController,
                        signUpViewModel = signUpViewModel,
                        moveToMain = moveToMain,
                    ) {
                        buttonEnabled = it
                    }
                }
            }
        }
        ProgressBarWithButton(
            currentProgress = currentProgress,
            progress = progressAnimation,
            buttonEnabled = buttonEnabled,
            isSuccessVerifyEmail = isSuccessVerifyEmail,
        ) {
            onNextButtonClicked()
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
            Caption(
                text = "$currentProgress / $maxProgress",
            )
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
                id = if (currentProgress == 1 && !isSuccessVerifyEmail) R.string.verification
                else R.string.next,
            ),
            color = JobisButtonColor.MainSolidColor,
            enabled = buttonEnabled,
        ) {
            onClick()
        }
    }
}

@Composable
private fun AuthLayoutHeaders(
    currentProgress: Int,
    titleList: List<Int>,
    onTopBarClicked: () -> Unit,
) {
    TopBar(
        text = stringResource(id = R.string.sign_up),
    ) {
        onTopBarClicked()
    }
    Spacer(modifier = Modifier.height(50.dp))
    Heading5(
        text = if (currentProgress in 1..3) stringResource(titleList[currentProgress])
        else stringResource(titleList.first()),
    )
}

private fun navigatePopBackStack(
    currentProgress: Int,
    moveToSignIn: () -> Unit,
): Int {
    if (currentProgress > 0) {
        return currentProgress - 1
    } else {
        moveToSignIn()
        return 0
    }
}