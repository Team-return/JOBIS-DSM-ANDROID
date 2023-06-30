package team.retum.jobis_android.feature.auth.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import team.retum.jobis_android.contract.ResetPasswordSideEffect
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading4

@Composable
internal fun ComparePasswordScreen(
    navController: NavController,
    resetPasswordViewModel: ResetPasswordViewModel,
) {

    val state = resetPasswordViewModel.container.stateFlow.collectAsState()

    LaunchedEffect(Unit){
        resetPasswordViewModel.container.sideEffectFlow.collect{
            when(it){
                is ResetPasswordSideEffect.SuccessVerification -> {
                    navController.navigate(JobisRoute.ResetPassword)
                }

                else -> {

                }
            }
        }
    }

    val onPasswordChanged = { password: String ->
        resetPasswordViewModel.setCurrentPassword(password)
        if(state.value.currentPassword.length != password.length){
            resetPasswordViewModel.setComparePasswordErrorState(
                comparePasswordErrorState = false,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 80.dp,
                start = 30.dp,
                end = 30.dp,
                bottom = 32.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
            value = state.value.currentPassword,
            onValueChanged = onPasswordChanged,
            hint = stringResource(id = R.string.hint_original_password),
            textFieldType = TextFieldType.PASSWORD,
            helperText = stringResource(id = R.string.password_format_error),
            errorText = stringResource(id = R.string.password_format_error),
            error = state.value.comparePasswordErrorState,
        )
        Spacer(modifier = Modifier.weight(1f))
        JobisLargeButton(
            text = stringResource(id = R.string.complete),
            enabled = state.value.currentPassword.isNotEmpty() && !state.value.comparePasswordErrorState,
        ){
            resetPasswordViewModel.comparePassword()
        }
    }
}