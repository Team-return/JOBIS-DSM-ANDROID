package team.retum.jobis_android.feature.signup

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.util.KeyboardOption
import team.retum.jobis_android.util.compose.ScreenLayout
import team.retum.jobisui.colors.ButtonColor
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.ui.theme.Heading5
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

@Composable
fun InputPersonalInformationScreen(
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    var sex by remember { mutableStateOf(Sex.MAN) }
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var `class` by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    var currentProgress by remember { mutableStateOf(0f) }

    val onManSelected = {
        sex = Sex.MAN
    }

    val onWomanSelected = {
        sex = Sex.WOMAN
    }

    val onNameChanged = { value: String ->
        name = value
    }

    val onGradeChanged = { value: String ->
        grade = value
    }

    val onClassChanged = { value: String ->
        `class` = value
    }

    val onNumberChanged = { value: String ->
        number = value
    }

    var onNextButtonClicked = {

    }

    ScreenLayout(
        onClick = { focusManager.clearFocus() },
        button = {
            ProgressWithCompleteButton(
                currentProgress = currentProgress,
                endProgress = 5f,
                onNextButtonClicked = onNextButtonClicked,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp,
                ),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            TopBar(
                onClick = { navController.popBackStack() },
            )
            Spacer(modifier = Modifier.height(50.dp))
            Heading5(
                text = stringResource(id = R.string.input_personal_information),
            )
            Spacer(modifier = Modifier.height(32.dp))
            SelectGender(
                onManSelected = onManSelected,
                onWomanSelected = onWomanSelected,
                sex = sex,
            )
            Spacer(modifier = Modifier.height(28.dp))
            SignUpInputs(
                name = name,
                grade = grade,
                `class` = `class`,
                number = number,
                onNameChanged = onNameChanged,
                onGradeChanged = onGradeChanged,
                onClassChanged = onClassChanged,
                onNumberChanged = onNumberChanged,
                focusManager = focusManager,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.7f))
        }
    }
}

@Composable
fun TopBar(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisImage(
            modifier = Modifier.padding(top = 2.dp),
            drawable = JobisIcon.LeftArrow,
        ) {
            onClick()
        }
        Spacer(modifier = Modifier.width(4.dp))
        Body1(
            text = stringResource(id = R.string.sign_up),
            color = JobisColor.Gray700,
        )
    }
}


@Composable
private fun SelectGender(
    onManSelected: () -> Unit,
    onWomanSelected: () -> Unit,
    sex: Sex,
) {

    var manButtonColor: ButtonColor
    var womanButtonColor: ButtonColor

    when (sex) {
        Sex.MAN -> {
            manButtonColor = JobisButtonColor.MainSolidColor
            womanButtonColor = JobisButtonColor.MainShadowColor
        }

        else -> {
            manButtonColor = JobisButtonColor.MainShadowColor
            womanButtonColor = JobisButtonColor.MainSolidColor
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(0.5f),
        ) {
            JobisMediumButton(
                text = Sex.MAN.value,
                color = manButtonColor,
                shadow = true,
            ) {
                onManSelected()
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            JobisMediumButton(
                text = Sex.WOMAN.value,
                color = womanButtonColor,
                shadow = true,
            ) {
                onWomanSelected()
            }
        }
    }
}

@Composable
private fun SignUpInputs(
    name: String,
    grade: String,
    `class`: String,
    number: String,
    onNameChanged: (String) -> Unit,
    onGradeChanged: (String) -> Unit,
    onClassChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    focusManager: FocusManager,
) {
    Column(

    ) {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            hint = stringResource(id = R.string.input_name_hint),
            onValueChanged = onNameChanged,
            value = name,
            keyboardOptions = KeyboardOption.Next,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier.weight(1f),
            ) {
                JobisBoxTextField(
                    color = JobisTextFieldColor.MainColor,
                    hint = stringResource(id = R.string.input_grade_hint),
                    onValueChanged = onGradeChanged,
                    value = grade,
                    keyboardOptions = KeyboardOption.Next,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f),
            ) {
                JobisBoxTextField(
                    color = JobisTextFieldColor.MainColor,
                    hint = stringResource(id = R.string.input_class_hint),
                    onValueChanged = onClassChanged,
                    value = `class`,
                    keyboardOptions = KeyboardOption.Next,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f),
            ) {
                JobisBoxTextField(
                    color = JobisTextFieldColor.MainColor,
                    hint = stringResource(id = R.string.input_number_hint),
                    onValueChanged = onNumberChanged,
                    value = number,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}

@Composable
private fun ProgressWithCompleteButton(
    currentProgress: Float,
    endProgress: Float,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.End,
    ) {
        Caption(
            text = "${currentProgress.toInt()} / ${endProgress.toInt()}",
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = (currentProgress / endProgress),
            backgroundColor = JobisColor.Gray500,
            color = JobisColor.LightBlue,
        )
        Spacer(modifier = Modifier.height(20.dp))
        JobisLargeButton(
            text = stringResource(id = R.string.next),
            color = JobisButtonColor.MainSolidColor,
            onClick = onNextButtonClicked,
        )
    }
}

enum class Sex(
    val value: String,
) {
    MAN("남성"),
    WOMAN("여성"),
}