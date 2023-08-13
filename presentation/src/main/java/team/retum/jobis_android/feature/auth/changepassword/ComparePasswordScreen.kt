package team.retum.jobis_android.feature.auth.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.resetpassword.ResetPasswordSideEffect
import team.retum.jobis_android.root.JobisAppState
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading4
import team.returm.jobisdesignsystem.toast.ToastType
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun ComparePasswordScreen(
    appState: JobisAppState,
    navigateToResetPassword: () -> Unit,
    resetPasswordViewModel: ResetPasswordViewModel,
) {

    val state by resetPasswordViewModel.container.stateFlow.collectAsState()

    val focusManager = LocalFocusManager.current

    val onComparePasswordButtonClicked = {
        resetPasswordViewModel.comparePassword()
    }

    val passwordMismatchMessage = stringResource(id = R.string.set_password_mismatch_password)

    LaunchedEffect(Unit) {
        resetPasswordViewModel.container.sideEffectFlow.collect {
            when (it) {
                is ResetPasswordSideEffect.SuccessVerification -> {
                    navigateToResetPassword()
                }

                is ResetPasswordSideEffect.PasswordMismatch -> {
                    appState.showToast(
                        message = passwordMismatchMessage,
                        toastType = ToastType.Error,
                    )
                }

                is ResetPasswordSideEffect.Exception -> {
                    appState.showToast(
                        message = it.message,
                        toastType = ToastType.Error,
                    )
                }

                else -> {}
            }
        }
    }

    val onPasswordChanged = { password: String ->
        resetPasswordViewModel.setCurrentPassword(password)
        if (state.currentPassword.length != password.length) {
            resetPasswordViewModel.setComparePasswordErrorState(
                comparePasswordErrorState = false,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable {
                focusManager.clearFocus()
            }
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Heading4(text = stringResource(id = R.string.check_original_password))
            Body4(
                text = stringResource(id = R.string.enter_original_password),
                color = JobisColor.Gray600,
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        JobisBoxTextField(
            value = state.currentPassword,
            onValueChanged = onPasswordChanged,
            hint = stringResource(id = R.string.hint_original_password),
            textFieldType = TextFieldType.PASSWORD,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_format_error),
            error = state.comparePasswordErrorState,
        )

        Spacer(modifier = Modifier.weight(1f))
        JobisLargeButton(
            text = stringResource(id = R.string.complete),
            enabled = state.currentPassword.isNotEmpty() && !state.comparePasswordErrorState,
            onClick = onComparePasswordButtonClicked,
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}