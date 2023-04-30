package team.retum.jobis_android.util.compose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.feature.signup.EmailVerificationScreen
import team.retum.jobis_android.feature.signup.InputPersonalInformationScreen
import team.retum.jobis_android.feature.signup.SetPasswordScreen
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.button.JobisLargeButton

@Composable
fun SignUpScreen(
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current

    var currentProgress by remember { mutableStateOf(0f) }
    var maxProgress by remember { mutableStateOf(3f) }

    var buttonText by remember { mutableStateOf("") }

    val progressAnimation by animateFloatAsState(
        targetValue = currentProgress / maxProgress,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing,
        )
    )

    BackHandler {
        if (currentProgress > 0f) {
            currentProgress -= 1f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable(
                rippleEnabled = false,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                focusManager.clearFocus()
            }
            .padding(
                horizontal = 20.dp,
                vertical = 36.dp,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBar(
                text = stringResource(id = R.string.sign_up),
            ) {
                if (currentProgress > 0f) {
                    currentProgress -= 1f
                } else if (currentProgress == 0f) {
                    navController.popBackStack()
                }
            }

            when (currentProgress) {
                0f -> {
                    InputPersonalInformationScreen()
                    buttonText = stringResource(id = R.string.next)
                }

                1f -> {
                    EmailVerificationScreen()
                    buttonText = stringResource(id = R.string.email_verification_check_verify)
                }

                2f -> {
                    SetPasswordScreen()
                    buttonText = stringResource(id = R.string.sign_up_complete)
                }

                3f -> {
                    // TODO implement signup completed logic
                }

                else -> navController.popBackStack()
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Caption(
                    text = "${currentProgress.toInt()} / ${maxProgress.toInt()}",
                    color = JobisColor.Gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = progressAnimation,
                    backgroundColor = JobisColor.Gray500,
                    color = JobisColor.LightBlue,
                )
                Spacer(modifier = Modifier.height(20.dp))
                JobisLargeButton(
                    text = buttonText,
                    color = JobisButtonColor.MainSolidColor,
                    onClick = {
                        if (currentProgress < 3f) {
                            currentProgress += 1f
                        }
                    },
                )
            }
        }
    }
}