package team.retum.jobis_android.feature.signup.verifyemail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.domain.param.user.AuthCodeType
import team.retum.jobis_android.contract.SignUpEvent
import team.retum.jobis_android.contract.SignUpSideEffect
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.button.JobisSmallButton
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import java.util.regex.Pattern

@Stable
val emailRegex = "^.+@dsm.hs.kr$"

@Composable
fun VerifyEmailScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
    changeButtonState: (Boolean) -> Unit,
    moveToSetPasswordScreen: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var authCode by remember { mutableStateOf("") }

    var enabledAuthCodeField by remember { mutableStateOf(false) }
    var enabledSendAuthCodeButton by remember { mutableStateOf(false) }

    var isEmailFieldError by remember { mutableStateOf(false) }

    val onEmailChanged = { value: String ->
        email = value
        enabledSendAuthCodeButton = email.isNotBlank()
        val error = Pattern.matches(emailRegex, email)
        isEmailFieldError = !error
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetEmail(
                email = email,
            )
        )
    }

    val onVerifyCodeChanged = { value: String ->
        authCode = value
        changeButtonState(authCode.isNotBlank())
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetVerifyCode(
                verifyCode = authCode,
            )
        )
    }

    val onSendVerificationCodeButtonClicked = {
        signUpViewModel.sendEvent(
            event = SignUpEvent.SendVerificationCode(
                email = email,
                authCodeType = AuthCodeType.SIGN_UP,
                userName = signUpViewModel.container.stateFlow.value.name,
            )
        )
        focusManager.clearFocus()
    }

    LaunchedEffect(Unit) {
        signUpViewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SignUpSideEffect.SendVerificationCodeSuccess -> {
                    // TODO 토스트 처리
                    enabledAuthCodeField = true
                }

                is SignUpSideEffect.EmailConflict -> {
                    // TODO 토스트 처리
                }

                is SignUpSideEffect.VerifyEmailSuccess -> {
                    moveToSetPasswordScreen()
                }

                is SignUpSideEffect.VerifyEmailUnAuthorized -> {

                }

                is SignUpSideEffect.VerifyEmailNotFound -> {

                }

                else -> {
                    // TODO 토스트 처리
                }
            }
        }
    }

    Column {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onEmailChanged,
            value = email,
            hint = stringResource(id = R.string.please_enter_email),
            helperText = stringResource(id = R.string.email_verification_email_hint),
            errorText = stringResource(id = R.string.email_verification_email_hint),
            imeAction = ImeAction.Next,
            isError = isEmailFieldError,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(3f),
            ) {
                JobisBoxTextField(
                    color = JobisTextFieldColor.MainColor,
                    onValueChanged = onVerifyCodeChanged,
                    value = authCode,
                    hint = stringResource(id = R.string.email_verification_verify_code_hint),
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    },
                    enabled = enabledAuthCodeField,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f),
            ) {
                JobisSmallButton(
                    text = stringResource(
                        id = if (enabledAuthCodeField) R.string.email_verification_resend
                        else R.string.email_verification_request_verify
                    ),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = onSendVerificationCodeButtonClicked,
                    enabled = enabledSendAuthCodeButton,
                )
            }
        }
    }
}