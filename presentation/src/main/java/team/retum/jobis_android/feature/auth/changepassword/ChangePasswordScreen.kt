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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
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
internal fun ChangePasswordScreen(
    navController: NavController,
) {
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
            accountId = "",
            onAccountIdChange = {},
            verificationCode = "",
            onVerificationCode = {}
        ) {

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
        ){

        }
    }
}

@Composable
private fun ChangePasswordInputs(
    accountId: String,
    onAccountIdChange: (String) -> Unit,
    verificationCode: String,
    onVerificationCode: (String) -> Unit,
    onRequestVerification: () -> Unit,
) {
    Column {
        JobisBoxTextField(
            onValueChanged = onAccountIdChange,
            value = accountId,
            hint = stringResource(id = R.string.please_enter_id),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(0.7f)) {
                JobisBoxTextField(
                    onValueChanged = onVerificationCode,
                    value = verificationCode,
                    hint = stringResource(id = R.string.verification_code),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(0.3f)) {
                JobisSmallButton(
                    text = stringResource(id = R.string.email_verification_request_verify),
                    color = JobisButtonColor.MainSolidColor,
                ) {
                    onRequestVerification()
                }
            }
        }
    }
}