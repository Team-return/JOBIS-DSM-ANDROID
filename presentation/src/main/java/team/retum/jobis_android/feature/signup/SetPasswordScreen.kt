package team.retum.jobis_android.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import team.retum.jobis_android.util.KeyboardOption
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.ui.theme.Heading5
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

@Composable
fun SetPasswordScreen() {

    val focusManager = LocalFocusManager.current

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val onPasswordChanged = { value: String ->
        password = value
    }

    val onRepeatPasswordChanged = { value: String ->
        repeatPassword = value
    }

    Column {
        Spacer(modifier = Modifier.height(50.dp))
        Heading5(
            text = stringResource(id = R.string.set_password),
        )
        Spacer(modifier = Modifier.height(32.dp))
        SetPasswordInputs(
            password = password,
            repeatPassword = repeatPassword,
            onPasswordChanged = onPasswordChanged,
            onRepeatPasswordChanged = onRepeatPasswordChanged,
            focusManager = focusManager,
        )
    }
}

@Composable
private fun SetPasswordInputs(
    password: String,
    repeatPassword: String,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    focusManager: FocusManager,
) {
    Column {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onPasswordChanged,
            value = password,
            hint = stringResource(id = R.string.sign_in_hint_password),
            keyboardType = KeyboardType.Password,
            keyboardOptions = KeyboardOption.Next,
        )
        Spacer(modifier = Modifier.height(12.dp))
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onRepeatPasswordChanged,
            value = repeatPassword,
            hint = stringResource(id = R.string.set_password_repeat_password_hint),
            keyboardType = KeyboardType.Password,
            keyboardOptions = KeyboardOption.Done,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            }
        )
    }
}