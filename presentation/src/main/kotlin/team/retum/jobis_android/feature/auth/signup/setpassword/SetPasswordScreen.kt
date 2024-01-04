package team.retum.jobis_android.feature.auth.signup.setpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.jobis_android.feature.auth.signup.SignUpViewModel
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType

@Composable
fun SetPasswordScreen(
    signUpViewModel: SignUpViewModel,
) {
    val state by signUpViewModel.collectAsState()
    val focusManager = LocalFocusManager.current

    PasswordFields(
        password = state.password,
        repeatPassword = state.repeatPassword,
        onPasswordChanged = signUpViewModel::setPassword,
        onRepeatPasswordChanged = signUpViewModel::setRepeatPassword,
        isPasswordError = state.passwordError,
        isRepeatPasswordError = state.repeatPasswordError,
        focusManager = focusManager,
    )
}

@Composable
private fun PasswordFields(
    password: String,
    repeatPassword: String,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    isPasswordError: Boolean,
    isRepeatPasswordError: Boolean,
    focusManager: FocusManager,
) {
    Column {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onPasswordChanged,
            value = password,
            hint = stringResource(id = R.string.sign_in_hint_password),
            helperText = stringResource(id = R.string.set_password_hint_password),
            errorText = stringResource(id = R.string.set_password_mismatch_password_format),
            imeAction = ImeAction.Next,
            textFieldType = TextFieldType.PASSWORD,
            error = isPasswordError,
        )
        Spacer(modifier = Modifier.height(12.dp))
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onRepeatPasswordChanged,
            value = repeatPassword,
            hint = stringResource(id = R.string.set_password_repeat_password_hint),
            errorText = stringResource(id = R.string.set_password_mismatch_password),
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            textFieldType = TextFieldType.PASSWORD,
            error = isRepeatPasswordError,
        )
    }
}
