package team.retum.jobis_android.feature.signup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.util.KeyboardOption
import team.retum.jobis_android.util.compose.TopBar
import team.retum.jobisui.colors.ButtonColor
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.ui.theme.Heading5
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.button.JobisSmallButton
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField

const val Duration = 300

@Composable
fun SignUpScreen(
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current

    var sex by remember { mutableStateOf(Sex.MAN) }
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var `class` by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var verifyCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

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

    val onEmailChanged = { value: String ->
        email = value
    }

    val onVerifyCodeChanged = { value: String ->
        verifyCode = value
    }

    val onPasswordChanged = { value: String ->
        password = value
    }

    val onRepeatPasswordChanged = { value: String ->
        repeatPassword = value
    }


    var currentProgress by remember { mutableStateOf(0f) }
    var maxProgress by remember { mutableStateOf(3f) }

    var buttonText by remember { mutableStateOf("") }

    val progressAnimation by animateFloatAsState(
        targetValue = currentProgress / maxProgress,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        )
    )


    val personalInfoScreenChangedAlpha by animateFloatAsState(
        targetValue = if (currentProgress == 0f) 1f else 0f,
        animationSpec = tween(
            durationMillis = Duration,
            easing = LinearEasing,
        )
    )

    val emailVerifyChangedAlpha by animateFloatAsState(
        targetValue = if (currentProgress == 1f) 1f else 0f,
        animationSpec = tween(
            durationMillis = Duration,
            easing = LinearEasing,
        )
    )

    val setPasswordScreenChangedAlpha by animateFloatAsState(
        targetValue = if (currentProgress == 2f) 1f else 0f,
        animationSpec = tween(
            durationMillis = Duration,
            easing = LinearEasing,
        )
    )

    BackHandler {
        if (currentProgress > 0f) {
            currentProgress -= 1f
        } else {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable(
                rippleEnabled = false,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                focusManager.clearFocus()
            }
            .padding(
                horizontal = 20.dp,
                vertical = 36.dp,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBar(
                text = stringResource(id = R.string.sign_up),
            ) {
                if (currentProgress > 0f) {
                    currentProgress -= 1f
                } else if (currentProgress == 0f) {
                    navController.popBackStack()
                }
            }

            when (currentProgress) {
                0f -> {
                    Box(
                        modifier = Modifier.alpha(
                            personalInfoScreenChangedAlpha,
                        )
                    ) {
                        PersonalInformationInputs(
                            sex = sex,
                            name = name,
                            grade = grade,
                            `class` = `class`,
                            number = number,
                            onManSelected = onManSelected,
                            onWomanSelected = onWomanSelected,
                            onNameChanged = onNameChanged,
                            onGradeChanged = onGradeChanged,
                            onClassChanged = onClassChanged,
                            onNumberChanged = onNumberChanged,
                            focusManager = focusManager,
                        )
                    }
                    buttonText = stringResource(id = R.string.next)
                }

                1f -> {
                    Box(
                        modifier = Modifier.alpha(
                            emailVerifyChangedAlpha,
                        )
                    ) {
                        EmailVerificationInputs(
                            email = email,
                            verifyCode = verifyCode,
                            onEmailChanged = onEmailChanged,
                            onVerifyCodeChanged = onVerifyCodeChanged,
                            focusManager = focusManager,
                        )
                    }
                    buttonText = stringResource(id = R.string.email_verification_check_verify)
                }

                2f -> {
                    Box(
                        modifier = Modifier.alpha(
                            setPasswordScreenChangedAlpha,
                        )
                    ) {
                        SetPasswordInputs(
                            password = password,
                            repeatPassword = repeatPassword,
                            onPasswordChanged = onPasswordChanged,
                            onRepeatPasswordChanged = onRepeatPasswordChanged,
                            focusManager = focusManager,
                        )
                    }
                    buttonText = stringResource(id = R.string.sign_up_complete)
                }

                3f -> {
                    // TODO implement signup completed logic
                }

                else -> navController.popBackStack()
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Caption(
                    text = "${currentProgress.toInt()} / ${maxProgress.toInt()}",
                    color = JobisColor.Gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = progressAnimation,
                    backgroundColor = JobisColor.Gray500,
                    color = JobisColor.LightBlue,
                )
                Spacer(modifier = Modifier.height(20.dp))
                JobisLargeButton(
                    text = buttonText,
                    color = JobisButtonColor.MainSolidColor,
                    onClick = {
                        if (currentProgress < 3f) {
                            currentProgress += 1f
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun PersonalInformationInputs(
    sex: Sex,
    name: String,
    grade: String,
    `class`: String,
    number: String,
    onManSelected: () -> Unit,
    onWomanSelected: () -> Unit,
    onNameChanged: (String) -> Unit,
    onGradeChanged: (String) -> Unit,
    onClassChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    focusManager: FocusManager,
) {

    Column(
        horizontalAlignment = Alignment.Start,
    ) {
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
    MAN("남성"),
    WOMAN("여성"),
}

@Composable
fun EmailVerificationInputs(
    email: String,
    verifyCode: String,
    onEmailChanged: (String) -> Unit,
    onVerifyCodeChanged: (String) -> Unit,
    focusManager: FocusManager,
) {

    val onRequestVerifyButtonClicked = {
        // TODO implement email verify business logic
    }

    Column {
        Spacer(modifier = Modifier.height(50.dp))
        Heading5(
            text = stringResource(id = R.string.email_verification),
        )
        Spacer(modifier = Modifier.height(32.dp))
        EmailVerifyInputs(
            email = email,
            verifyCode = verifyCode,
            onEmailChanged = onEmailChanged,
            onVerifyCodeChanged = onVerifyCodeChanged,
            focusManager = focusManager,
            onVerifyButtonClicked = onRequestVerifyButtonClicked,
        )
    }
}

@Composable
private fun EmailVerifyInputs(
    email: String,
    verifyCode: String,
    onEmailChanged: (String) -> Unit,
    onVerifyCodeChanged: (String) -> Unit,
    onVerifyButtonClicked: () -> Unit,
    focusManager: FocusManager,
) {
    Column {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onEmailChanged,
            value = email,
            hint = stringResource(id = R.string.please_enter_email),
            helperText = stringResource(id = R.string.email_verification_email_hint),
            keyboardOptions = KeyboardOption.Next,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(3f),
            ) {
                JobisBoxTextField(
                    color = JobisTextFieldColor.MainColor,
                    onValueChanged = onVerifyCodeChanged,
                    value = verifyCode,
                    hint = stringResource(id = R.string.email_verification_verify_code_hint),
                    keyboardType = KeyboardType.NumberPassword,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    }
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f),
            ) {
                JobisSmallButton(
                    text = stringResource(id = R.string.email_verification_request_verify),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = onVerifyButtonClicked,
                )
            }
        }
    }
}

@Composable
fun SetPasswordInputs(
    password: String,
    repeatPassword: String,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    focusManager: FocusManager,
) {

    Column {
        Spacer(modifier = Modifier.height(50.dp))
        Heading5(
            text = stringResource(id = R.string.set_password),
        )
        Spacer(modifier = Modifier.height(32.dp))
        PasswordFields(
            password = password,
            repeatPassword = repeatPassword,
            onPasswordChanged = onPasswordChanged,
            onRepeatPasswordChanged = onRepeatPasswordChanged,
            focusManager = focusManager,
        )
    }
}

@Composable
private fun PasswordFields(
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