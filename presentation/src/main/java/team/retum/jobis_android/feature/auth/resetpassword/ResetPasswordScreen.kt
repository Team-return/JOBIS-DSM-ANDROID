package team.retum.jobis_android.feature.auth.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.ResetPasswordSideEffect
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading4

@Composable
internal fun ResetPasswordScreen(
    navController: NavController,
    resetPasswordViewModel: ResetPasswordViewModel,
) {

    val state = resetPasswordViewModel.container.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        resetPasswordViewModel.container.sideEffectFlow.collect {
            when (it) {
                is ResetPasswordSideEffect.SuccessResetPassword -> {
                    navController.navigate(JobisRoute.Main) {
                        popUpTo(JobisRoute.Main) {
                            inclusive = true
                        }
                    }
                }

                else -> {

                }
            }
        }
    }

    val onNewPasswordChanged = { newPassword: String ->
        resetPasswordViewModel.setNewPassword(newPassword = newPassword)
    }

    val onPasswordRepeatChanged = { passwordRepeat: String ->
        resetPasswordViewModel.setPasswordRepeat(passwordRepeat = passwordRepeat)
    }

    val newPassword = state.value.newPassword
    val passwordRepeat = state.value.passwordRepeat

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 80.dp,
                start = 30.dp,
                end = 30.dp,
                bottom = 32.dp,
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
        JobisImage(drawable = R.drawable.ic_reset_password)
        Spacer(modifier = Modifier.height(30.dp))
        ResetPasswordInput(
            newPassword = newPassword,
            passwordRepeat = passwordRepeat,
            passwordFormatErrorState = state.value.passwordFormatErrorState,
            passwordRepeatErrorState = state.value.passwordRepeatErrorState,
            onNewPasswordChanged = onNewPasswordChanged,
            onPasswordRepeatChanged = onPasswordRepeatChanged,
        )
        Spacer(modifier = Modifier.weight(1f))
        JobisLargeButton(
            text = stringResource(id = R.string.complete),
            color = JobisButtonColor.MainSolidColor,
            enabled = newPassword.isNotEmpty() && passwordRepeat.isNotEmpty() && !state.value.passwordFormatErrorState && !state.value.passwordRepeatErrorState,
        ) {
            resetPasswordViewModel.resetPassword()
        }
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
            hint = stringResource(id = R.string.input_new_password)
        )
        Spacer(modifier = Modifier.height(16.dp))
        JobisBoxTextField(
            onValueChanged = onPasswordRepeatChanged,
            value = passwordRepeat,
            error = passwordRepeatErrorState,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_repeat_error),
            hint = stringResource(id = R.string.password_repeat_error),
        )
    }
}