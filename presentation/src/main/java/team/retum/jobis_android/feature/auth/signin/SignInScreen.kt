package team.retum.jobis_android.feature.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.signin.SignInSideEffect
import team.retum.jobis_android.root.JobisAppState
import team.retum.jobis_android.viewmodel.signin.SignInViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisCheckBoxColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.checkbox.JobisCheckBox
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading1
import team.returm.jobisdesignsystem.toast.ToastType
import team.returm.jobisdesignsystem.util.Animated
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun SignInScreen(
    appState: JobisAppState,
    signInViewModel: SignInViewModel = hiltViewModel(),
    navigateToMainWithPopUpSignIn: () -> Unit,
    navigateToResetPasswordVerifyEmail: () -> Unit,
    navigateToSignUp: () -> Unit,
) {

    val state by signInViewModel.container.stateFlow.collectAsState()

    var showBackgroundIcon by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        showBackgroundIcon = true
        signInViewModel.container.sideEffectFlow.collect {
            when (it) {
                is SignInSideEffect.MoveToMain -> {
                    navigateToMainWithPopUpSignIn()
                }

                is SignInSideEffect.UnAuthorization -> {
                    signInViewModel.setPasswordError(true)
                }

                is SignInSideEffect.NotFound -> {
                    signInViewModel.setEmailError(true)
                }

                is SignInSideEffect.Exception -> {
                    appState.showToast(
                        message = it.message,
                        toastType = ToastType.Error,
                    )
                }
            }
        }

    }

    val onEmailChanged = { email: String ->
        signInViewModel.setEmail(email)
        signInViewModel.setEmailError(false)
    }

    val onPasswordChanged = { password: String ->
        signInViewModel.setPassword(password)
        signInViewModel.setPasswordError(false)
    }

    val onSignInCheckChanged = {
        signInViewModel.setAutoSignIn(!state.autoSignIn)
        focusManager.clearFocus()
    }

    val onResetPasswordClicked = {
        navigateToResetPasswordVerifyEmail()
    }

    val onDoSignUpClicked = {
        navigateToSignUp()
    }

    val postLogin = {
        signInViewModel.postLogin()
    }

    Box {
        Column {
            Animated(visible = showBackgroundIcon) {
                JobisImage(
                    modifier = Modifier.offset(
                        x = 120.dp,
                        y = (-220).dp,
                    ),
                    drawable = R.drawable.ic_sign_in_background,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .jobisClickable { focusManager.clearFocus() }
                .padding(
                    top = 112.dp,
                    start = 20.dp,
                    end = 20.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                contentAlignment = Alignment.TopStart,
            ) {
                Caption(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.jobis_description),
                )
                Heading1(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = R.string.jobis),
                    color = JobisColor.LightBlue,
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            SignInInputs(
                email = state.email,
                password = state.password,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                emailError = state.emailError,
                passwordError = state.passwordError,
            )
            Spacer(modifier = Modifier.height(22.dp))
            SignInOptions(
                autoSignInChecked = state.autoSignIn,
                onSignInCheckChanged = onSignInCheckChanged,
                onResetPasswordClicked = onResetPasswordClicked,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.jobisClickable(onClick = onDoSignUpClicked),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Caption(
                    text = stringResource(id = R.string.sign_in_sign_up_question),
                    color = JobisColor.Gray600,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Caption(
                    text = stringResource(id = R.string.sign_up),
                    decoration = TextDecoration.Underline,
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            JobisLargeButton(
                text = stringResource(id = R.string.sign_in),
                color = JobisButtonColor.MainSolidColor,
                onClick = postLogin,
                enabled = state.signInButtonEnabled,
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SignInInputs(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    emailError: Boolean,
    passwordError: Boolean,
) {
    JobisBoxTextField(
        color = JobisTextFieldColor.MainColor,
        value = email,
        onValueChanged = onEmailChanged,
        hint = stringResource(id = R.string.sign_in_hint_email),
        error = emailError,
        errorText = stringResource(id = R.string.sign_in_email_error),
    )
    Spacer(modifier = Modifier.height(12.dp))
    JobisBoxTextField(
        color = JobisTextFieldColor.MainColor,
        value = password,
        onValueChanged = onPasswordChanged,
        hint = stringResource(id = R.string.sign_in_hint_password),
        error = passwordError,
        errorText = stringResource(id = R.string.sign_in_password_error),
        textFieldType = TextFieldType.PASSWORD,
    )
}

@Composable
private fun SignInOptions(
    autoSignInChecked: Boolean,
    onSignInCheckChanged: () -> Unit,
    onResetPasswordClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.jobisClickable(onClick = onSignInCheckChanged),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JobisCheckBox(
                color = JobisCheckBoxColor.MainColor,
                isChecked = autoSignInChecked,
                onChecked = onSignInCheckChanged,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Caption(text = stringResource(id = R.string.sign_in_auto_sign_in))
        }
        Caption(
            modifier = Modifier.jobisClickable(onClick = onResetPasswordClicked),
            text = stringResource(id = R.string.sign_in_reset_password),
            color = JobisColor.Gray600,
        )
    }
}