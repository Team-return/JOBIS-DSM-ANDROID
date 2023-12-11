package team.retum.jobis_android.feature.auth.signup.verifyemail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobis.jobis_android.R
import team.retum.jobis_android.feature.auth.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisSmallButton
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

@Composable
fun VerifyEmailScreen(
    signUpViewModel: SignUpViewModel,
) {
    val state by signUpViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val onVerifyCodeChanged = { verifyCode: String ->
        signUpViewModel.setVerifyCode(verifyCode = verifyCode)
        if (verifyCode.length == 6) focusManager.clearFocus()
    }

    val onSendVerificationCodeButtonClicked = {
        signUpViewModel.sendVerificationCode()
        focusManager.clearFocus()
    }

    Column {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = signUpViewModel::setEmail,
            value = state.email,
            hint = stringResource(id = R.string.please_enter_email),
            helperText = stringResource(id = R.string.email_verification_email_hint),
            errorText = stringResource(id = R.string.email_verification_email_hint),
            imeAction = ImeAction.Next,
            error = state.emailError,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.weight(3f)) {
                JobisBoxTextField(
                    color = JobisTextFieldColor.MainColor,
                    onValueChanged = onVerifyCodeChanged,
                    value = state.verifyCode,
                    hint = stringResource(id = R.string.email_verification_verify_code_hint),
                    keyboardActions = KeyboardActions { focusManager.clearFocus() },
                    enabled = state.authCodeEnabled,
                    error = state.verifyCodeError,
                    errorText = stringResource(id = R.string.auth_code_mismatch),
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.weight(1f)) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    JobisSmallButton(
                        text = stringResource(
                            id = if (state.authCodeEnabled) {
                                R.string.email_verification_resend
                            } else {
                                R.string.email_verification_request_verify
                            },
                        ),
                        color = JobisButtonColor.MainSolidColor,
                        onClick = onSendVerificationCodeButtonClicked,
                        enabled = state.sendVerifyCodeButtonEnabled,
                    )
                }
            }
        }
    }
}
