package team.retum.jobis_android.feature.auth.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import team.retum.jobis_android.LocalAppState
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisSmallButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading4
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun ResetPasswordVerifyEmailScreen(
    navigateToResetPassword: () -> Unit,
    resetPasswordViewModel: ResetPasswordViewModel,
) {
    val appState = LocalAppState.current
    val context = LocalContext.current
    val state by resetPasswordViewModel.collectAsState()
    val focusManager = LocalFocusManager.current
    val email = state.email
    val authCode = state.authCode
    val sendAuthCodeState = state.sendAuthCodeState

    resetPasswordViewModel.collectSideEffect {
        when (it) {
            is ResetPasswordSideEffect.SuccessVerification -> {
                navigateToResetPassword()
            }

            is ResetPasswordSideEffect.Exception -> {
                appState.showErrorToast(context.getString(it.message))
            }

            is ResetPasswordSideEffect.ClearFocus -> {
                focusManager.clearFocus()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .jobisClickable { focusManager.clearFocus() }
            .padding(
                top = 80.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 32.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Heading4(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.self_verification),
        )
        Body4(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.self_verification_explain),
            color = JobisColor.Gray600,
        )
        Image(
            modifier = Modifier
                .fillMaxSize(0.4f)
                .padding(vertical = 10.dp),
            painter = painterResource(id = R.drawable.ic_reset_password_verify_email),
            contentDescription = stringResource(id = R.string.content_description_image_reset_password_verify),
        )
        ChangePasswordInputs(
            email = email,
            emailErrorState = state.emailErrorState,
            onEmailChanged = resetPasswordViewModel::setEmail,
            authCode = authCode,
            authCodeErrorState = state.authCodeErrorState,
            sendAuthCodeState = sendAuthCodeState,
            onAuthCodeChanged = resetPasswordViewModel::setAuthCode,
            onRequestVerification = resetPasswordViewModel::sendVerificationCode,
        )
        Spacer(modifier = Modifier.weight(1f))
        Caption(
            text = stringResource(id = R.string.check_personal_information),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        JobisLargeButton(
            text = stringResource(id = R.string.do_verify),
            color = JobisButtonColor.MainSolidColor,
            enabled = email.isNotEmpty() && authCode.isNotEmpty() && sendAuthCodeState,
            onClick = resetPasswordViewModel::verifyEmail,
        )
    }
}

@Composable
private fun ChangePasswordInputs(
    email: String,
    emailErrorState: Boolean,
    onEmailChanged: (String) -> Unit,
    authCode: String,
    authCodeErrorState: Boolean,
    onAuthCodeChanged: (String) -> Unit,
    sendAuthCodeState: Boolean,
    onRequestVerification: () -> Unit,
) {
    JobisBoxTextField(
        onValueChanged = onEmailChanged,
        value = email,
        hint = stringResource(id = R.string.please_enter_email),
        error = emailErrorState,
        errorText = stringResource(id = R.string.sign_in_email_error),
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row {
        Box(modifier = Modifier.weight(0.75f)) {
            JobisBoxTextField(
                onValueChanged = onAuthCodeChanged,
                value = authCode,
                hint = stringResource(id = R.string.verification_code),
                error = authCodeErrorState,
                errorText = stringResource(id = R.string.auth_code_mismatch),
                enabled = sendAuthCodeState,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(0.25f)
                .padding(top = 8.dp),
        ) {
            JobisSmallButton(
                text = stringResource(
                    id = if (sendAuthCodeState) {
                        R.string.email_verification_resend
                    } else {
                        R.string.email_verification_request_verify
                    },
                ),
                color = JobisButtonColor.MainSolidColor,
                enabled = email.isNotEmpty(),
                onClick = onRequestVerification,
            )
        }
    }
}
