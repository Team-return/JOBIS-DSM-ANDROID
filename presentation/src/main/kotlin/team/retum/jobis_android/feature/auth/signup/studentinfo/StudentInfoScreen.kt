package team.retum.jobis_android.feature.auth.signup.studentinfo

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.domain.enums.Gender
import team.retum.jobis_android.feature.auth.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun StudentInfoScreen(
    signUpViewModel: SignUpViewModel,
) {
    val state by signUpViewModel.collectAsState()
    val focusManager = LocalFocusManager.current
    val clearFocus = {
        focusManager.clearFocus()
    }
    val onGradeChanged: (String) -> Unit = { grade: String ->
        signUpViewModel.setGrade(grade = grade.take(1))
        if (grade.length == 1) {
            focusManager.moveFocus(FocusDirection.Next)
        }
    }
    val onClassChanged: (String) -> Unit = { `class`: String ->
        signUpViewModel.setClass(`class` = `class`.take(2))
        if (`class`.length == 1) {
            focusManager.moveFocus(FocusDirection.Next)
        }
    }
    val onNumberChanged: (String) -> Unit = { number: String ->
        signUpViewModel.setNumber(number = number.take(2))
        if (number.length == 2) {
            clearFocus()
        }
    }

    LaunchedEffect(Unit) {
        with(state) {
            val nameBlank = name.isNotBlank()
            val gradeBlank = grade.isNotBlank()
            val classRoomBlank = classRoom.isNotBlank()
            val numberBlank = number.isNotBlank()
            signUpViewModel.setSignUpButtonEnabled(
                nameBlank && gradeBlank && classRoomBlank && numberBlank,
            )
        }
    }

    Column(
        modifier = Modifier.jobisClickable(onClick = clearFocus),
        horizontalAlignment = Alignment.Start,
    ) {
        SelectGender(
            onManSelected = { signUpViewModel.setGender(gender = Gender.MAN) },
            onWomanSelected = { signUpViewModel.setGender(gender = Gender.WOMAN) },
            gender = state.gender,
        )
        Spacer(modifier = Modifier.height(28.dp))
        InformationFields(
            name = state.name,
            grade = state.grade,
            classRoom = state.classRoom,
            number = state.number,
            studentNotFound = state.studentNotFound,
            onNameChanged = signUpViewModel::setName,
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
    gender: Gender,
) {
    val (manButtonColor, womanButtonColor) = when (gender) {
        Gender.MAN -> {
            JobisButtonColor.MainSolidColor to JobisButtonColor.MainShadowColor
        }

        Gender.WOMAN -> {
            JobisButtonColor.MainShadowColor to JobisButtonColor.MainSolidColor
        }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(1f)) {
            JobisMediumButton(
                text = Gender.MAN.value,
                color = manButtonColor,
                shadow = true,
                onClick = onManSelected,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(modifier = Modifier.weight(1f)) {
            JobisMediumButton(
                text = Gender.WOMAN.value,
                color = womanButtonColor,
                shadow = true,
                onClick = onWomanSelected,
            )
        }
    }
}

@Composable
private fun InformationFields(
    name: String,
    grade: String,
    classRoom: String,
    number: String,
    studentNotFound: Boolean,
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
        imeAction = ImeAction.Next,
        error = studentNotFound,
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Box(
            modifier = Modifier.weight(0.9f),
        ) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                hint = stringResource(id = R.string.input_hint_grade),
                onValueChanged = onGradeChanged,
                value = grade,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword,
                error = studentNotFound,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(modifier = Modifier.weight(0.9f)) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                hint = stringResource(id = R.string.input_hint_class),
                onValueChanged = onClassChanged,
                value = classRoom,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword,
                error = studentNotFound,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(modifier = Modifier.weight(0.9f)) {
            JobisBoxTextField(
                color = JobisTextFieldColor.MainColor,
                hint = stringResource(id = R.string.input_hint_number),
                onValueChanged = onNumberChanged,
                value = number,
                keyboardType = KeyboardType.NumberPassword,
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                error = studentNotFound,
            )
        }
    }
}
