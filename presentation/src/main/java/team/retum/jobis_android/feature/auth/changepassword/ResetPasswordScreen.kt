package team.retum.jobis_android.feature.auth.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading4

@Composable
internal fun ResetPasswordScreen(
    navController: NavController,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel(),
) {

    val state = resetPasswordViewModel.container.stateFlow.collectAsState()

    val onPasswordChanged = { password: String ->
        resetPasswordViewModel.setPassword(password = password)
    }

    val onPasswordRepeatChanged = { passwordRepeat: String ->
        resetPasswordViewModel.setPasswordRepeat(passwordRepeat = passwordRepeat)
    }

    val password = state.value.password
    val passwordRepeat = state.value.passwordRepeat

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
            text = stringResource(id = R.string.reset_password),
        )
        Body4(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.reset_password_explain),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(26.dp))
        JobisImage(drawable = R.drawable.ic_reset_password)
        Spacer(modifier = Modifier.height(30.dp))
        ResetPasswordInput(
            password = password,
            passwordRepeat = passwordRepeat,
            passwordFormatErrorState = state.value.passwordFormatErrorState,
            passwordRepeatErrorState = state.value.passwordRepeatErrorState,
            onPasswordChanged = onPasswordChanged,
            onPasswordRepeatChanged = onPasswordRepeatChanged,
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.72f))
        JobisLargeButton(
            text = stringResource(id = R.string.complete),
            color = JobisButtonColor.MainSolidColor,
            //enabled = password.isNotEmpty() && passwordRepeat.isNotEmpty(),
        ) {
            navController.navigate(JobisRoute.ResetPasswordVerifyEmail)
        }
    }
}

@Composable
private fun ResetPasswordInput(
    password: String,
    passwordRepeat: String,
    passwordFormatErrorState: Boolean,
    passwordRepeatErrorState: Boolean,
    onPasswordChanged: (String) -> Unit,
    onPasswordRepeatChanged: (String) -> Unit,
) {
    Column {
        JobisBoxTextField(
            onValueChanged = onPasswordChanged,
            value = password,
            error = passwordFormatErrorState,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_format_error),
            hint = stringResource(id = R.string.input_new_password)
        )
        Spacer(modifier = Modifier.height(16.dp))
        JobisBoxTextField(
            onValueChanged = onPasswordRepeatChanged,
            value = passwordRepeat,
            error = passwordRepeatErrorState,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_repeat_error),
            hint = stringResource(id = R.string.password_repeat_error),
        )
    }
}