package team.retum.jobis_android.feature.signup.verifyemail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.util.KeyboardOption
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.button.JobisSmallButton
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

@Composable
fun VerifyEmailScreen(
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var verifyCode by remember { mutableStateOf("") }

    val onEmailChanged = { value: String ->
        email = value
    }

    val onVerifyCodeChanged = { value: String ->
        verifyCode = value
    }

    val onRequestVerifyButtonClicked = {
        // TODO implement email verify business logic
    }

    EmailVerifyInputs(
        email = email,
        verifyCode = verifyCode,
        onEmailChanged = onEmailChanged,
        onVerifyCodeChanged = onVerifyCodeChanged,
        focusManager = focusManager,
        onVerifyButtonClicked = onRequestVerifyButtonClicked,
    )
}

@Composable
private fun EmailVerifyInputs(
    email: String,
    verifyCode: String,
    onEmailChanged: (String) -> Unit,
    onVerifyCodeChanged: (String) -> Unit,
    onVerifyButtonClicked: () -> Unit,
    focusManager: FocusManager,
) {
    Column {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onEmailChanged,
            value = email,
            hint = stringResource(id = R.string.please_enter_email),
            helperText = stringResource(id = R.string.email_verification_email_hint),
            keyboardOptions = KeyboardOption.Next,
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
                    value = verifyCode,
                    hint = stringResource(id = R.string.email_verification_verify_code_hint),
                    keyboardType = KeyboardType.NumberPassword,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    }
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f),
            ) {
                JobisSmallButton(
                    text = stringResource(id = R.string.email_verification_request_verify),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = onVerifyButtonClicked,
                )
            }
        }
    }
}