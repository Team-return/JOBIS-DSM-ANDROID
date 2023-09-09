package team.retum.jobis_android.feature.auth.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.resetpassword.ResetPasswordSideEffect
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading4
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun ResetPasswordScreen(
    navigateToMain: () -> Unit,
    resetPasswordViewModel: ResetPasswordViewModel,
) {

    val appState = LocalAppState.current

    val state by resetPasswordViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val newPassword = state.newPassword
    val passwordRepeat = state.passwordRepeat

    LaunchedEffect(Unit) {
        resetPasswordViewModel.container.sideEffectFlow.collect {
            when (it) {
                is ResetPasswordSideEffect.SuccessResetPassword -> {
                    navigateToMain()
                }

                is ResetPasswordSideEffect.Exception -> {
                    appState.showErrorToast(message = it.message)
                }

                else -> {}
            }
        }
    }

    val onResetPasswordButtonClicked = {
        resetPasswordViewModel.resetPassword()
    }

    val onNewPasswordChanged = { newPassword: String ->
        resetPasswordViewModel.setNewPassword(newPassword = newPassword)
    }

    val onPasswordRepeatChanged = { passwordRepeat: String ->
        resetPasswordViewModel.setPasswordRepeat(passwordRepeat = passwordRepeat)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable {
                focusManager.clearFocus()
            }
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Heading4(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.reset_password),
        )
        Body4(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.reset_password_explain),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(26.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_reset_password),
            contentDescription = stringResource(id = R.string.content_description_icon_reset_password),
        )
        Spacer(modifier = Modifier.height(30.dp))
        ResetPasswordInput(
            newPassword = newPassword,
            passwordRepeat = passwordRepeat,
            passwordFormatErrorState = state.passwordFormatErrorState,
            passwordRepeatErrorState = state.passwordRepeatErrorState,
            onNewPasswordChanged = onNewPasswordChanged,
            onPasswordRepeatChanged = onPasswordRepeatChanged,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.imePadding(),
        ) {
            JobisLargeButton(
                text = stringResource(id = R.string.complete),
                color = JobisButtonColor.MainSolidColor,
                enabled = newPassword.isNotEmpty() && passwordRepeat.isNotEmpty() && !state.passwordFormatErrorState && !state.passwordRepeatErrorState,
                onClick = onResetPasswordButtonClicked,
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ResetPasswordInput(
    newPassword: String,
    passwordRepeat: String,
    passwordFormatErrorState: Boolean,
    passwordRepeatErrorState: Boolean,
    onNewPasswordChanged: (String) -> Unit,
    onPasswordRepeatChanged: (String) -> Unit,
) {
    Column {
        JobisBoxTextField(
            onValueChanged = onNewPasswordChanged,
            value = newPassword,
            error = passwordFormatErrorState,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_format_error),
            hint = stringResource(id = R.string.input_new_password),
            textFieldType = TextFieldType.PASSWORD,
        )
        Spacer(modifier = Modifier.height(16.dp))
        JobisBoxTextField(
            onValueChanged = onPasswordRepeatChanged,
            value = passwordRepeat,
            error = passwordRepeatErrorState,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_repeat_error),
            hint = stringResource(id = R.string.password_repeat_error),
            textFieldType = TextFieldType.PASSWORD
        )
    }
}