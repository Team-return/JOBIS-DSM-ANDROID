package team.retum.jobis_android.feature.auth.changepassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
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
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel(),
){

    val onPasswordChanged = { password: String ->
        
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 80.dp,
                start = 30.dp,
                end = 30.dp,
                bottom = 32.dp,
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Column {
            Heading4(text = stringResource(id = R.string.check_original_password))
            Body4(
                text = stringResource(id = R.string.enter_original_password),
                color = JobisColor.Gray600,
            )
            Spacer(modifier = Modifier.height(28.dp))
            JobisBoxTextField(
                onValueChanged = onPasswordChanged,
                hint = stringResource(id = R.string.hint_original_password),
                textFieldType = TextFieldType.PASSWORD,
                helperText = stringResource(id = R.string.password_format_error),
                errorText = stringResource(id = R.string.password_format_error),
            )
        }
        JobisLargeButton(
            text = stringResource(id = R.string.complete),
            onClick = {},
        )
    }
}