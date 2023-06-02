package team.retum.jobis_android.feature.signin

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisCheckBoxColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.checkbox.JobisCheckBox
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading1
import team.returm.jobisdesignsystem.util.Animated

@Composable
internal fun SignInScreen(
    navController: NavController,
) {

    var show by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        show = true
    }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var autoSignInChecked by remember { mutableStateOf(false) }

    val onEmailChanged = { value: String ->
        email = value
    }

    val onPasswordChanged = { value: String ->
        password = value
    }

    val onSignInCheckChange = {
        autoSignInChecked = !autoSignInChecked
    }

    Box {
        Column {
            Animated(visible = show) {
                JobisImage(
                    modifier = Modifier.offset(
                        x = (120).dp,
                        y = (-220).dp,
                    ),
                    drawable = R.drawable.ic_splash,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 112.dp,
                    start = 20.dp,
                    end = 20.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                contentAlignment = Alignment.TopStart,
            ) {
                Caption(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.jobis_description),
                )
                Heading1(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = R.string.jobis),
                    color = JobisColor.LightBlue,
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            SignInInputs(
                email = email,
                password = password,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
            )
            Spacer(modifier = Modifier.height(22.dp))
            SignInOptions(
                autoSignInChecked = autoSignInChecked,
            ) {
                onSignInCheckChange()
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.7f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Caption(
                    text = stringResource(id = R.string.sign_in_sign_up_question),
                    color = JobisColor.Gray600,
                )
                Caption(
                    text = stringResource(id = R.string.sign_up),
                    decoration = TextDecoration.Underline,
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            JobisLargeButton(
                text = stringResource(id = R.string.sign_in),
                color = JobisButtonColor.MainSolidColor,
                onClick = {},
            )   
        }
    }
}

@Composable
private fun SignInInputs(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    JobisBoxTextField(
        color = JobisTextFieldColor.MainColor,
        value = email,
        onValueChanged = onEmailChanged,
        hint = stringResource(id = R.string.sign_in_hint_email),
    )
    Spacer(modifier = Modifier.height(12.dp))
    JobisBoxTextField(
        color = JobisTextFieldColor.MainColor,
        value = password,
        onValueChanged = onPasswordChanged,
        hint = stringResource(id = R.string.sign_in_hint_password),
        textFieldType = TextFieldType.PASSWORD,
    )
}

@Composable
private fun SignInOptions(
    autoSignInChecked: Boolean,
    onSignInCheckChanged: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.jobisClickable(
                rippleEnabled = false,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onSignInCheckChanged()
            },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JobisCheckBox(
                color = JobisCheckBoxColor.MainColor,
                isChecked = autoSignInChecked,
                onChecked = onSignInCheckChanged,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Caption(text = stringResource(id = R.string.sign_in_auto_sign_in))
        }
        Caption(
            text = stringResource(id = R.string.sign_in_reset_password),
            color = JobisColor.Gray600,
        )
    }
}