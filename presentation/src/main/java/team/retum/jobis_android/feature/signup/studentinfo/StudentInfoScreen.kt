package team.retum.jobis_android.feature.signup.studentinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import team.retum.jobis_android.contract.SignUpEvent
import team.retum.jobis_android.contract.SignUpSideEffect
import team.retum.jobis_android.util.KeyboardOption
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.ButtonColor
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

@Composable
fun StudentInfoScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
    changeButtonStatus: (Boolean) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    var sex by remember { mutableStateOf(Sex.MAN) }
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var `class` by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    var isNumberError by remember { mutableStateOf(false) }

    LaunchedEffect(sex) {
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetSex(
                sex = sex,
            )
        )
    }

    LaunchedEffect(Unit) {
        signUpViewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SignUpSideEffect.CheckStudentExistsSuccess -> {
                    changeButtonStatus(true)
                }

                is SignUpSideEffect.CheckStudentExistsNotFound -> {
                    // TODO 토스트 처리
                    isNumberError = true
                }

                else -> {
                    // TODO 토스트 처리
                }
            }
        }
    }

    val onManSelected = {
        sex = Sex.MAN
    }

    val onWomanSelected = {
        sex = Sex.WOMAN
    }

    val changeButtonStatus = {
        changeButtonStatus(
            name.isNotBlank() && grade.isNotBlank() && `class`.isNotBlank() && number.isNotBlank(),
        )
    }

    val onNameChanged = { value: String ->
        name = value
        changeButtonStatus()
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetName(
                name = name,
            )
        )
    }

    val onGradeChanged = { value: String ->
        grade = value
        changeButtonStatus()
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetGrade(
                grade = grade,
            )
        )
    }

    val onClassChanged = { value: String ->
        `class` = value
        changeButtonStatus()
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetClass(
                `class` = `class`,
            )
        )
    }

    val onNumberChanged = { value: String ->
        number = value
        changeButtonStatus()
        signUpViewModel.sendEvent(
            event = SignUpEvent.SetNumber(
                number = number,
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        SelectGender(
            onManSelected = onManSelected,
            onWomanSelected = onWomanSelected,
            sex = sex,
        )
        Spacer(modifier = Modifier.height(28.dp))
        InformationFields(
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
            modifier = Modifier.weight(1f),
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
            modifier = Modifier.weight(1f),
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
private fun InformationFields(
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
    JobisBoxTextField(
        color = JobisTextFieldColor.MainColor,
        hint = stringResource(id = R.string.input_hint_name),
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
                hint = stringResource(id = R.string.input_hint_grade),
                onValueChanged = onGradeChanged,
                value = grade,
                keyboardOptions = KeyboardOption.Next,
                keyboardType = KeyboardType.Number,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier.weight(1f),
        ) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                hint = stringResource(id = R.string.input_hint_class),
                onValueChanged = onClassChanged,
                value = `class`,
                keyboardOptions = KeyboardOption.Next,
                keyboardType = KeyboardType.Number,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier.weight(1f),
        ) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                hint = stringResource(id = R.string.input_hint_number),
                onValueChanged = onNumberChanged,
                value = number,
                keyboardType = KeyboardType.Number,
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
            )
        }
    }
}

enum class Sex(
    val value: String,
) {
    MAN("남"), WOMAN("여")
}

private fun setButtonStatus(
    name: String,
    grade: String,
    `class`: String,
    number: String,
) = (name.isNotBlank() && grade.isNotBlank() && `class`.isNotBlank() && number.isNotBlank())