package team.retum.jobis_android.feature.auth.signin

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import team.retum.jobis_android.util.Animated
import team.retum.jobis_android.util.Next
import team.retum.jobis_android.viewmodel.signin.SignInViewModel
import team.retum.jobis_android.viewmodel.signin.SignInViewModel.SignInEvent
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.image.JobisImage
import team.retum.jobisui.textfield.JobisUnderLineTextField
import team.retum.jobisui.ui.theme.Body4
import team.returm.jobisdesignsystem.button.JobisLargeButton

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {

    val container = viewModel.container.sideEffectFlow
    val state = viewModel.container.stateFlow.collectAsState().value

    var isShowLoginButton by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val onIdChanged = { id: String ->
        viewModel.onEvent(
            event = SignInEvent.SetId(
                id = id,
            )
        )
    }

    val onPasswordChanged = { password: String ->
        viewModel.onEvent(
            event = SignInEvent.SetPassword(
                password = password,
            )
        )
    }

    BackHandler {
        isLogin = false
    }

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

            Animated(
                visible = isShowLoginButton,
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.4f))
            }
        }
        Box {
            Column {
                Animated(
                    visible = isShowLoginButton,
                    isBounce = true,
                ) {
                    SplashBackgroundImage()
                }
            }

            Column {
                Animated(
                    visible = isLogin,
                    isBounce = true,
                ) {
                    SignInText()
                }
            }
        }
        LoginSheet(
            isShowLoginButton = isShowLoginButton,
            isLogin = isLogin,
            onLoginButtonClick = {
                if (!isLogin) {
                    isLogin = true
                } else {
                    viewModel.postLogin()
                }
            },
            id = state.accountId,
            password = state.password,
            onIdChanged = onIdChanged,
            onPasswordChanged = onPasswordChanged,
            focusManager = focusManager,
        )
    }
}

@Composable
fun LoginSheet(
    isShowLoginButton: Boolean,
    isLogin: Boolean,
    onLoginButtonClick: () -> Unit,
    id: String,
    password: String,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    focusManager: FocusManager,
) {

    val padding by animateFloatAsState(
        if (isLogin) 0.45f
        else 0.04f,
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    .background(
                        color = JobisColor.Gray100,
                    )
                    .padding(
                        horizontal = 20.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Animated(
                    visible = isLogin,
                ) {
                    LoginInput(
                        id = id,
                        password = password,
                        onIdChanged = onIdChanged,
                        onPasswordChanged = onPasswordChanged,
                        focusManager = focusManager,
                    )
                }

                Spacer(modifier = Modifier.fillMaxHeight(padding))

                Animated(
                    visible = !isLogin,
                ) {
                    Body4(
                        text = stringResource(id = R.string.sign_in_start_service)
                    )
                }

                Animated(
                    visible = isLogin,
                ) {
                    Body4(
                        text = stringResource(id = R.string.sign_in_forget_password)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                JobisLargeButton(
                    text = stringResource(id = R.string.sign_in_kr),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = {
                        onLoginButtonClick()
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Animated(
                    visible = !isLogin,
                ) {
                    JobisLargeButton(
                        text = stringResource(id = R.string.sign_up),
                        color = JobisButtonColor.MainGhostColor,
                        onClick = {},
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
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

@Composable
fun SignInText() {
    Row(
        horizontalArrangement = Arrangement.Start,
    ) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 82.dp,
            )
        ) {
            JobisImage(
                drawable = R.drawable.ic_welcome,
            )
            Spacer(modifier = Modifier.height(8.dp))
            JobisImage(
                drawable = R.drawable.ic_sign_in
            )
        }
        JobisImage(
            drawable = R.drawable.ic_splash_right,
        )
    }
}

@Composable
fun LoginInput(
    id: String,
    password: String,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    focusManager: FocusManager,
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 20.dp,
            vertical = 44.dp,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        JobisUnderLineTextField(
            color = JobisTextFieldColor.UnderLineColor,
            onValueChanged = onIdChanged,
            value = id,
            hint = stringResource(id = R.string.sign_in_id_hint),
            fieldText = stringResource(id = R.string.id),
            keyboardOptions = Next,
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        JobisUnderLineTextField(
            color = JobisTextFieldColor.UnderLineColor,
            onValueChanged = onPasswordChanged,
            value = password,
            hint = stringResource(id = R.string.sign_in_password_hint),
            fieldText = stringResource(id = R.string.password),
            isPassword = true,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            }
        )
    }
}