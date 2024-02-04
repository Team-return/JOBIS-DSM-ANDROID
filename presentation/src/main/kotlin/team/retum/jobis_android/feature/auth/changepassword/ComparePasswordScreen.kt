package team.retum.jobis_android.feature.auth.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordSideEffect
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordViewModel
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading4
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun ComparePasswordScreen(
    navigateToResetPassword: () -> Unit,
    resetPasswordViewModel: ResetPasswordViewModel,
) {
    val appState = LocalAppState.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val state by resetPasswordViewModel.collectAsState()
    val currentPassword = resetPasswordViewModel.currentPassword

    resetPasswordViewModel.collectSideEffect {
        when (it) {
            is ResetPasswordSideEffect.SuccessVerification -> {
                navigateToResetPassword()
            }

            is ResetPasswordSideEffect.PasswordMismatch -> {
                appState.showErrorToast(context.getString(R.string.set_password_mismatch_password))
            }

            is ResetPasswordSideEffect.Exception -> {
                appState.showErrorToast(context.getString(it.message))
            }

            else -> {}
        }
    }

    val onPasswordChanged = { password: String ->
        resetPasswordViewModel.setCurrentPassword(password)
        if (currentPassword.length != password.length) {
            resetPasswordViewModel.setComparePasswordErrorState(
                comparePasswordErrorState = false,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable { focusManager.clearFocus() }
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
            value = currentPassword,
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
            enabled = currentPassword.isNotEmpty() && !state.comparePasswordErrorState,
            onClick = resetPasswordViewModel::comparePassword,
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}
