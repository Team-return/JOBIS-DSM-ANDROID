package team.retum.jobis_android.feature.auth.changepassword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.ChangePasswordSideEffect
import team.retum.jobis_android.viewmodel.changepassword.ChangePasswordViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisSmallButton
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading4

@Composable
internal fun ChangePasswordVerifyEmailScreen(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {

    val state = changePasswordViewModel.container.stateFlow.collectAsState()

    val onEmailChanged = { email: String ->
        changePasswordViewModel.setEmail(email = email)
    }

    val onAuthCodeChanged = { authCode: String ->
        changePasswordViewModel.setAuthCode(authCode = authCode)
    }

    val email = state.value.email
    val authCode = state.value.authCode
    val sendAuthCodeState = state.value.sendAuthCodeState

    LaunchedEffect(Unit) {
        changePasswordViewModel.container.sideEffectFlow.collect {
            when (it) {
                is ChangePasswordSideEffect.SuccessVerification -> {
//                    navController.navigate()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 100.dp,
                start = 30.dp,
                end = 30.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
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
        Spacer(modifier = Modifier.height(26.dp))
        JobisImage(drawable = R.drawable.ic_change_password)
        Spacer(modifier = Modifier.height(30.dp))
        ChangePasswordInputs(
            email = email,
            emailErrorState = state.value.emailErrorState,
            onEmailChanged = onEmailChanged,
            authCode = authCode,
            authCodeErrorState = state.value.authCodeErrorState,
            sendAuthCodeState = sendAuthCodeState,
            onAuthCodeChanged = onAuthCodeChanged,
        ) {
            changePasswordViewModel.sendVerificationCode()
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.7f))
        Caption(
            text = stringResource(id = R.string.check_personal_information),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(24.dp))
        JobisLargeButton(
            text = stringResource(id = R.string.do_verify),
            color = JobisButtonColor.MainSolidColor,
            enabled = email.isNotEmpty() && authCode.isNotEmpty() && sendAuthCodeState,
        ) {
            changePasswordViewModel.verifyEmail()
        }
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
    Column {
        JobisBoxTextField(
            onValueChanged = onEmailChanged,
            value = email,
            hint = stringResource(id = R.string.please_enter_email),
            error = emailErrorState,
            errorText = stringResource(id = R.string.sign_in_email_error),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(0.7f)) {
                JobisBoxTextField(
                    onValueChanged = onAuthCodeChanged,
                    value = authCode,
                    hint = stringResource(id = R.string.verification_code),
                    error = authCodeErrorState,
                    errorText = stringResource(id = R.string.auth_code_mismatch),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(0.3f)) {
                JobisSmallButton(
                    text = stringResource(
                        id = if (sendAuthCodeState) R.string.email_verification_resend
                        else R.string.email_verification_request_verify,
                    ),
                    color = JobisButtonColor.MainSolidColor,
                    enabled = email.isNotEmpty(),
                ) {
                    onRequestVerification()
                }
            }
        }
    }
}