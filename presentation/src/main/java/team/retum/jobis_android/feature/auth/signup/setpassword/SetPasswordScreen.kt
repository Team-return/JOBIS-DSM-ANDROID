package team.retum.jobis_android.feature.auth.signup.setpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.SignUpEvent
import team.retum.jobis_android.contract.SignUpSideEffect
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import java.util.regex.Pattern

// TODO 패스워드 에러 관리 로직 리팩토링
@Stable
val passwordRegex =
    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#\$%^&*()+|=])[A-Za-z\\d~!@#\$%^&*()+|=]{8,16}\$"

@Composable
fun SetPasswordScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
    moveToMain: () -> Unit,
    changeButtonStatus: (Boolean) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var isPasswordError by remember { mutableStateOf(false) }
    var isRepeatPasswordError by remember { mutableStateOf(false) }

    val onPasswordChanged = { value: String ->
        password = value
        changeButtonStatus(
            checkPassword(
                password = password,
                repeatPassword = repeatPassword,
            )
        )
        isPasswordError = !Pattern.matches(passwordRegex, password)
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetPassword(
                password = password,
            )
        )
    }

    val onRepeatPasswordChanged = { value: String ->
        repeatPassword = value
        changeButtonStatus(
            checkPassword(
                password = password,
                repeatPassword = repeatPassword,
            )
        )
        isRepeatPasswordError = password != repeatPassword
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetRepeatPassword(
                repeatPassword = repeatPassword,
            )
        )
    }

    LaunchedEffect(Unit) {
        signUpViewModel.container.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.SignUpSuccess -> {
                    moveToMain()
                }

                is SignUpSideEffect.SignUpConflict -> {

                }

                else -> {

                }
            }
        }
    }

    PasswordFields(
        password = password,
        repeatPassword = repeatPassword,
        onPasswordChanged = onPasswordChanged,
        onRepeatPasswordChanged = onRepeatPasswordChanged,
        isPasswordError = isPasswordError,
        isRepeatPasswordError = isRepeatPasswordError,
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

private fun checkPassword(
    password: String,
    repeatPassword: String,
): Boolean = (password.isNotEmpty() && repeatPassword.isNotEmpty() && password == repeatPassword)