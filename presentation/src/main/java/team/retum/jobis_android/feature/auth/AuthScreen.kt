package team.retum.jobis_android.feature.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import team.retum.jobis_android.contract.SignInSideEffect
import team.retum.jobis_android.util.Next
import team.retum.jobis_android.util.UnderLineTextFieldWrapper
import team.retum.jobis_android.viewmodel.signin.SignInViewModel
import team.retum.jobis_android.viewmodel.signin.SignInViewModel.SignInEvent
import team.retum.jobisui.button.JobisRadioButton
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisCheckBoxColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisDropDownColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.ui.theme.Body4
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.dropdown.JobisDropDown
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisUnderLineTextField
import team.returm.jobisdesignsystem.util.Animated

val InputPadding = Modifier.padding(
    start = 20.dp,
    end = 20.dp,
    top = 20.dp,
)

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {

    val container = viewModel.container.sideEffectFlow

    var isShowSignInSheet by remember { mutableStateOf(false) }
    var isSignIn by remember { mutableStateOf(false) }
    var isSignUp by remember { mutableStateOf(false) }

    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var signUpPassword by remember { mutableStateOf("") }
    var signUpPasswordRepeat by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isGenderChecked by remember { mutableStateOf(0) }

    val focusManager = LocalFocusManager.current

    var isIdError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isSignUpPasswordError by remember { mutableStateOf(false) }
    var isSignUpPasswordRepeatError by remember { mutableStateOf(false) }

    val onIdChanged = { value: String ->
        if (id.length != value.length) {
            isIdError = false
        }
        id = value
        viewModel.onEvent(
            event = SignInEvent.SetId(
                id = id,
            )
        )
    }

    val onPasswordChanged = { value: String ->
        if (password.length != value.length) {
            isPasswordError = false
        }
        password = value
        viewModel.onEvent(
            event = SignInEvent.SetPassword(
                password = password,
            )
        )
    }

    val onEmailChanged = { value: String ->
        if (email.length != value.length) {
            isEmailError = false
        }
        email = value
    }

    val onSignUpPasswordChange = { value: String ->
        if (signUpPassword.length != value.length) {
            isSignUpPasswordError = false
        }
        signUpPassword = value
    }

    val onSignUpPasswordRepeatChanged = { value: String ->
        if (signUpPasswordRepeat.length != value.length) {
            isSignUpPasswordRepeatError = false
        }
        signUpPasswordRepeat = value
    }

    val onNameChanged = { value: String ->
        name = value
    }

    val onMaleChecked = { value: Boolean ->
        isGenderChecked = if(value) 1 else 0
    }

    val onFemaleChecked = { value: Boolean ->
        isGenderChecked = if(value) 2 else 0
    }


    val maxHeight by animateFloatAsState(
        if (isSignIn) 0.62f
        else if (isSignUp) 0.64f
        else 0.04f,
    )

    BackHandler {
        isSignIn = false
        isSignUp = false
    }

    LaunchedEffect(Unit) {
        delay(3000)
        isShowSignInSheet = true

        container.collect {
            when (it) {
                is SignInSideEffect.MoveToMain -> {
                    // TODO implement success event
                }

                is SignInSideEffect.UnAuthorization -> {
                    isPasswordError = true
                }

                is SignInSideEffect.NotFound -> {
                    isIdError = true
                }

                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        JobisColor.DarkBlue,
                        JobisColor.LightBlue,
                    ),
                ),
            ),
    ) {

        SplashMainImage(
            isShowSignInSheet = isShowSignInSheet,
        )

        SplashBackgroundImages(
            isShowSignInSheet = isShowSignInSheet,
            isSignIn = isSignIn,
            isSignUp = isSignUp,
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Animated(
                visible = isShowSignInSheet,
            ) {
                Column(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                            )
                        )
                        .fillMaxWidth()
                        .background(
                            color = JobisColor.Gray100,
                        )
                        .padding(
                            horizontal = 20.dp,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    SignInInputs(
                        maxHeight = maxHeight,
                        isSignIn = isSignIn,
                        isIdError = isIdError,
                        isPasswordError = isPasswordError,
                        id = id,
                        password = password,
                        onIdChanged = onIdChanged,
                        onPasswordChanged = onPasswordChanged,
                        focusManager = focusManager,
                    )

                    SignUpInputs(
                        maxHeight = maxHeight,
                        isSignUp = isSignUp,
                        isEmailError = isEmailError,
                        isSignUpPasswordError = isSignUpPasswordError,
                        isSignUpPasswordRepeatError = isSignUpPasswordRepeatError,
                        email = email,
                        signUpPassword = signUpPassword,
                        signUpPasswordRepeat = signUpPasswordRepeat,
                        name = name,
                        isGenderChecked = isGenderChecked,
                        onEmailChanged = onEmailChanged,
                        onSignUpPasswordChange = onSignUpPasswordChange,
                        onSignUpPasswordRepeatChanged = onSignUpPasswordRepeatChanged,
                        onNameChanged = onNameChanged,
                        onMaleChecked = onMaleChecked,
                        onFemaleChecked = onFemaleChecked,
                        focusManager = focusManager,
                        maxNumber = 20,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SignInSheetComponents(
                        isSignIn = isSignIn,
                        isSignUp = isSignUp,
                        isSignInChange = { isSignIn = true },
                        isSignUpChange = { isSignUp = true },
                        viewModel = viewModel,
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SplashMainImage(
    isShowSignInSheet: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        JobisImage(
            modifier = Modifier.size(145.dp),
            drawable = R.drawable.ic_logo,
        )


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
        ) {
            JobisImage(
                modifier = Modifier.size(
                    width = 142.dp,
                    height = 60.dp,
                ),
                drawable = R.drawable.ic_jobis_text,
            )

            Spacer(modifier = Modifier.width(4.dp))
        }

        Animated(
            visible = isShowSignInSheet,
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.4f))
        }
    }
}

@Composable
fun AuthText(
    isSignIn: Boolean,
    isSignUp: Boolean,
) {
    Column {
        Animated(
            visible = isSignIn,
            isBounce = true,
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 82.dp,
                    ),
                    horizontalAlignment = Alignment.Start,
                ) {
                    JobisImage(
                        drawable = R.drawable.ic_welcome,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    JobisImage(
                        drawable = R.drawable.ic_sign_in
                    )
                }
                JobisImage(
                    drawable = R.drawable.ic_splash_right,
                )
            }
        }

        Animated(
            visible = isSignUp,
            isBounce = true,
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 82.dp,
                    ),
                    horizontalAlignment = Alignment.Start,
                ) {
                    JobisImage(
                        drawable = R.drawable.ic_welcome,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    JobisImage(
                        drawable = R.drawable.ic_sign_up
                    )
                }
                JobisImage(
                    drawable = R.drawable.ic_splash_right,
                )
            }
        }
    }
}

@Composable
fun SplashBackgroundImages(
    isShowSignInSheet: Boolean,
    isSignIn: Boolean,
    isSignUp: Boolean,
) {
    Box {
        Column {
            Animated(
                visible = isShowSignInSheet,
                isBounce = true,
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(0.7f),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        JobisImage(
                            modifier = Modifier.size(
                                width = 132.dp,
                                height = 184.dp,
                            ),
                            drawable = R.drawable.ic_splash_left,
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.4f))
                        JobisImage(
                            modifier = Modifier.size(
                                184.dp,
                            ),
                            drawable = R.drawable.ic_splash_right,
                        )
                    }
                }
            }
        }
        AuthText(
            isSignIn = isSignIn,
            isSignUp = isSignUp,
        )
    }
}

@Composable
fun ColumnScope.SignInInputs(
    maxHeight: Float,
    isSignIn: Boolean,
    isIdError: Boolean,
    isPasswordError: Boolean,
    id: String,
    password: String,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    focusManager: FocusManager,
) {
    Animated(
        visible = isSignIn,
    ) {
        Column(
            modifier = InputPadding
                .fillMaxHeight(maxHeight)
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnderLineTextFieldWrapper {
                JobisUnderLineTextField(
                    color = JobisTextFieldColor.UnderLineColor,
                    onValueChanged = onIdChanged,
                    value = id,
                    hint = stringResource(id = R.string.sign_in_id_hint),
                    fieldText = stringResource(id = R.string.id),
                    keyboardOptions = Next,
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    error = isIdError,
                    helperText = stringResource(id = R.string.sign_in_id_error),
                )
            }

            UnderLineTextFieldWrapper {
                JobisUnderLineTextField(
                    color = JobisTextFieldColor.UnderLineColor,
                    onValueChanged = onPasswordChanged,
                    value = password,
                    hint = stringResource(id = R.string.sign_in_password_hint),
                    fieldText = stringResource(id = R.string.password),
                    isPassword = true,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    },
                    error = isPasswordError,
                    helperText = stringResource(id = R.string.sign_in_password_error),
                )
            }
        }
    }
}

@Composable
fun ColumnScope.SignUpInputs(
    maxHeight: Float,
    isSignUp: Boolean,
    isEmailError: Boolean,
    isSignUpPasswordError: Boolean,
    isSignUpPasswordRepeatError: Boolean,
    email: String,
    signUpPassword: String,
    signUpPasswordRepeat: String,
    name: String,
    isGenderChecked: Int,
    onEmailChanged: (String) -> Unit,
    onSignUpPasswordChange: (String) -> Unit,
    onSignUpPasswordRepeatChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onMaleChecked: (Boolean) -> Unit,
    onFemaleChecked: (Boolean) -> Unit,
    maxNumber: Int,
    focusManager: FocusManager,
) {

    val numberList = mutableListOf<String>()

    repeat(maxNumber) {
        numberList.add((it + 1).toString())
    }

    Animated(
        visible = isSignUp,
    ) {
        Column(
            modifier = InputPadding
                .fillMaxHeight(maxHeight)
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnderLineTextFieldWrapper {
                JobisUnderLineTextField(
                    color = JobisTextFieldColor.UnderLineColor,
                    hint = stringResource(id = R.string.sign_in_id_hint),
                    onValueChanged = onEmailChanged,
                    value = email,
                    fieldText = stringResource(id = R.string.email),
                    helperText = stringResource(id = R.string.sign_up_email_error),
                    error = isEmailError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                )
            }

            UnderLineTextFieldWrapper {
                JobisUnderLineTextField(
                    color = JobisTextFieldColor.UnderLineColor,
                    hint = stringResource(id = R.string.sign_up_password_hint),
                    onValueChanged = onSignUpPasswordChange,
                    value = signUpPassword,
                    fieldText = stringResource(id = R.string.password),
                    helperText = stringResource(id = R.string.sign_in_password_error),
                    error = isSignUpPasswordError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                )
            }

            UnderLineTextFieldWrapper {
                JobisUnderLineTextField(
                    color = JobisTextFieldColor.UnderLineColor,
                    hint = stringResource(id = R.string.sign_up_password_hint),
                    onValueChanged = onSignUpPasswordRepeatChanged,
                    value = signUpPasswordRepeat,
                    fieldText = stringResource(id = R.string.password_repeat),
                    helperText = stringResource(id = R.string.sign_up_password_repeat_error),
                    error = isSignUpPasswordRepeatError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                )
            }

            UnderLineTextFieldWrapper {
                JobisUnderLineTextField(
                    color = JobisTextFieldColor.UnderLineColor,
                    hint = stringResource(id = R.string.sign_up_name_hint),
                    onValueChanged = onNameChanged,
                    value = name,
                    fieldText = stringResource(id = R.string.name),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Body4(
                    text = stringResource(id = R.string.gender),
                )

                Spacer(modifier = Modifier.fillMaxWidth(0.55f))

                JobisRadioButton(
                    color = JobisCheckBoxColor.MainColor,
                    isChecked = isGenderChecked == 1,
                ) {
                    onMaleChecked(it)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Body4(
                    text = stringResource(id = R.string.male),
                )

                Spacer(modifier = Modifier.width(22.dp))

                JobisRadioButton(
                    color = JobisCheckBoxColor.MainColor,
                    isChecked = isGenderChecked == 2,
                ) {
                    onFemaleChecked(it)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Body4(
                    text = stringResource(id = R.string.female),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box {
                Row {
                    Box(
                        modifier = Modifier.fillMaxWidth(0.33f),
                    ) {
                        JobisDropDown(
                            color = JobisDropDownColor.MainColor,
                            itemList = listOf("1", "2", "3"),
                            title = stringResource(id = R.string.grade),
                        ) {

                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(0.52f),
                    ) {
                        JobisDropDown(
                            color = JobisDropDownColor.MainColor,
                            itemList = listOf("1", "2", "3", "4"),
                            title = stringResource(id = R.string.class_room),
                        ) {

                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Box {
                        JobisDropDown(
                            color = JobisDropDownColor.MainColor,
                            itemList = numberList,
                            title = stringResource(id = R.string.number),
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.SignInSheetComponents(
    isSignIn: Boolean,
    isSignUp: Boolean,
    isSignInChange: () -> Unit,
    isSignUpChange: () -> Unit,
    viewModel: SignInViewModel,
) {

    Animated(
        visible = !isSignIn && !isSignUp,
    ) {
        Body4(
            text = stringResource(id = R.string.sign_in_start_service)
        )
    }

    Animated(
        visible = isSignIn,
    ) {
        Body4(
            text = stringResource(id = R.string.sign_in_forget_password)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Animated(
        visible = !isSignUp,
    ) {
        JobisLargeButton(
            text = stringResource(id = R.string.sign_in_kr),
            color = JobisButtonColor.MainSolidColor,
            onClick = {
                if (!isSignIn) {
                    isSignInChange()
                } else {
                    viewModel.postLogin()
                }
            },
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Animated(
        visible = !isSignIn,
    ) {
        JobisLargeButton(
            text = stringResource(id = R.string.sign_up),
            color = JobisButtonColor.MainGhostColor,
            onClick = {
                isSignUpChange()
            },
        )
    }
}