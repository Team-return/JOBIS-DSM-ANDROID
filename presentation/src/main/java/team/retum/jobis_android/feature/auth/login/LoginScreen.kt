package team.retum.jobis_android.feature.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import team.retum.jobis_android.viewmodel.splash.SplashViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.image.JobisImage
import team.retum.jobisui.ui.theme.Body4
import team.returm.jobisdesignsystem.button.JobisLargeButton

@Composable
fun SplashScreen(
    navController: NavController,
    vm: SplashViewModel = hiltViewModel(),
) {

    var isShowLoginButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000)
        isShowLoginButton = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = JobisColor.LightBlue,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            JobisImage(
                modifier = Modifier.size(145.dp),
                drawable = R.drawable.ic_logo,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
            ) {
                JobisImage(
                    modifier = Modifier.size(
                        width = 142.dp,
                        height = 60.dp,
                    ),
                    drawable = R.drawable.ic_jobis_text,
                )

                Spacer(modifier = Modifier.width(4.dp))
            }

            AnimatedVisibility(isShowLoginButton) {
                Spacer(modifier = Modifier.fillMaxHeight(0.4f))
            }
        }
        Column {
            AnimatedVisibility(
                visible = isShowLoginButton,
                enter = fadeIn(
                    animationSpec = tween(1500),
                ).plus(
                    expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow,
                        )
                    )
                ),
            ) {
                team.retum.jobis_android.feature.auth.login.SplashBackgroundImage()
            }
        }
        team.retum.jobis_android.feature.auth.login.LoginComponent(isShowLoginButton)
    }
}

@Composable
fun LoginComponent(
    isShowLoginButton: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        AnimatedVisibility(
            visible = isShowLoginButton
        ) {
            Column(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                        )
                    )
                    .fillMaxWidth()
                    .height(248.dp)
                    .background(
                        color = JobisColor.Gray100,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Body4(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(id = R.string.login_start_service)
                )

                Spacer(modifier = Modifier.height(16.dp))

                JobisLargeButton(
                    text = stringResource(id = R.string.login),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = { /*TODO*/ },
                )

                Spacer(modifier = Modifier.height(16.dp))

                JobisLargeButton(
                    text = stringResource(id = R.string.register),
                    color = JobisButtonColor.MainGhostColor,
                    onClick = {},
                )
            }
        }
    }
}

@Composable
fun SplashBackgroundImage() {
    Column(
        modifier = Modifier.fillMaxHeight(0.7f),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JobisImage(
                modifier = Modifier.size(
                    width = 132.dp,
                    height = 184.dp,
                ),
                drawable = R.drawable.ic_splash_left,
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.4f))
            JobisImage(
                modifier = Modifier.size(
                    184.dp,
                ),
                drawable = R.drawable.ic_splash_right,
            )
        }
    }
}