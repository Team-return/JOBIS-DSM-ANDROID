package team.retum.jobis_android.feature.signin

import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.SignInEvent
import team.retum.jobis_android.contract.SignInSideEffect
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.clearTextFieldError
import team.retum.jobis_android.viewmodel.signin.SignInViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisCheckBoxColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.ui.theme.Heading1
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.checkbox.JobisCheckBox
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAutoLogin by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    val onEmailChanged = { value: String ->
        isEmailError = clearTextFieldError(
            isError = isEmailError,
            inputValue = value,
            textFieldValue = email,
        )
        email = value
        signInViewModel.sendEvent(
            event = SignInEvent.SetEmail(
                email = email,
            )
        )
    }

    val onPasswordChanged = { value: String ->
        isPasswordError = clearTextFieldError(
            isError = isPasswordError,
            inputValue = value,
            textFieldValue = password,
        )
        password = value
        signInViewModel.sendEvent(
            event = SignInEvent.SetPassword(
                password = password,
            )
        )
    }

    val onAutoLoginChanged = { value: Boolean ->
        isAutoLogin = value
    }

    val onResetPasswordClicked = {

    }

    val onSignUpTextClicked = {
        navController.navigate(JobisRoute.SignUp)
    }

    val onSignInButtonClicked = {
        signInViewModel.sendEvent(
            event = SignInEvent.PostLogin,
        )
    }

    LaunchedEffect(Unit) {
        signInViewModel.container.sideEffectFlow.collect {
            when (it) {
                is SignInSideEffect.MoveToMain -> {
                    navController.navigate(JobisRoute.Main)
                }

                is SignInSideEffect.NotFound -> {
                    isEmailError = true
                }

                is SignInSideEffect.UnAuthorization -> {
                    isPasswordError = true
                }

                is SignInSideEffect.Exception -> {

                }
            }
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
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(84.dp))
            SignInTitle()
            Spacer(modifier = Modifier.height(80.dp))
            SignInInput(
                email = email,
                password = password,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                focusManager = focusManager,
                isEmailError = isEmailError,
                isPasswordError = isPasswordError,
            )
            Spacer(modifier = Modifier.height(22.dp))
            SignInOptions(
                isChecked = isAutoLogin,
                onCheckBoxClicked = onAutoLoginChanged,
                onResetPasswordClicked = onResetPasswordClicked,
                interactionSource = interactionSource,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.7f))
            SignInSignUpText(
                onSignUpTextClicked = onSignUpTextClicked,
                interactionSource = interactionSource,
            )
            Spacer(modifier = Modifier.height(22.dp))
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            JobisLargeButton(
                text = stringResource(id = R.string.sign_in),
                color = JobisButtonColor.MainSolidColor,
                onClick = onSignInButtonClicked,
                enabled = (email.isNotBlank() && password.isNotBlank()),
            )
        }
    }
}


@Composable
fun SignInTitle() {
    Box(
        modifier = Modifier.padding(start = 10.dp),
    ) {
        Caption(
            modifier = Modifier.padding(start = 12.dp),
            text = stringResource(id = R.string.jobis_description),
        )
        Column(
            modifier = Modifier.height(68.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Heading1(
                text = stringResource(id = R.string.jobis),
                color = JobisColor.LightBlue,
            )
        }
    }
}

@Composable
fun SignInInput(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    focusManager: FocusManager,
    isEmailError: Boolean,
    isPasswordError: Boolean,
) {
    Column(
        modifier = Modifier,
    ) {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            hint = stringResource(id = R.string.sign_in_hint_email),
            onValueChanged = onEmailChanged,
            value = email,
            keyboardType = KeyboardType.Email,
            isError = isEmailError,
            errorText = stringResource(id = R.string.sign_in_email_error),
        )
        Spacer(modifier = Modifier.height(8.dp))
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            hint = stringResource(id = R.string.sign_in_hint_password),
            onValueChanged = onPasswordChanged,
            value = password,
            keyboardType = KeyboardType.Password,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            isPassword = true,
            isError = isPasswordError,
            errorText = stringResource(id = R.string.sign_in_password_error),
        )
    }
}

@Composable
fun SignInOptions(
    isChecked: Boolean,
    onCheckBoxClicked: (Boolean) -> Unit,
    onResetPasswordClicked: () -> Unit,
    interactionSource: MutableInteractionSource,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.width(90.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JobisCheckBox(
                color = JobisCheckBoxColor.MainColor,
                isChecked = isChecked,
                onChecked = onCheckBoxClicked,
            )
            Caption(
                text = stringResource(id = R.string.sign_in_auto_sign_in),
            )
        }
        Caption(
            modifier = Modifier
                .padding(end = 4.dp)
                .jobisClickable(
                    rippleEnabled = false,
                    interactionSource = interactionSource,
                ) {
                    onResetPasswordClicked()
                },
            text = stringResource(id = R.string.sign_in_reset_password),
            color = JobisColor.Gray600,
        )
    }
}

@Composable
fun SignInSignUpText(
    onSignUpTextClicked: () -> Unit,
    interactionSource: MutableInteractionSource,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .jobisClickable(
                rippleEnabled = false,
                interactionSource = interactionSource,
            ) {
                onSignUpTextClicked()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Caption(
            text = stringResource(id = R.string.sign_in_sign_up_question),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Caption(
            text = stringResource(id = R.string.sign_up),
        )
        JobisImage(
            modifier = Modifier.padding(top = 2.dp),
            drawable = JobisIcon.RightArrow,
        ) {
            onSignUpTextClicked()
        }
    }
}