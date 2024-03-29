package team.retum.jobis_android.feature.auth.signup

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.enums.AuthCodeType
import team.retum.domain.enums.Gender
import team.retum.domain.exception.ConflictException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.SignUpParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.usecase.user.CheckStudentExistUseCase
import team.retum.domain.usecase.user.SendVerificationCodeUseCase
import team.retum.domain.usecase.user.SignUpUseCase
import team.retum.domain.usecase.user.VerifyEmailUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.Regex
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    internal val checkStudentExistUseCase: CheckStudentExistUseCase,
    internal val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    internal val verifyEmailUseCase: VerifyEmailUseCase,
    internal val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect>() {

    override val container = container<SignUpState, SignUpSideEffect>(SignUpState())

    var email by mutableStateOf("")
        private set
    var verifyCode by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var repeatPassword by mutableStateOf("")
        private set
    var grade by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set
    var classRoom by mutableStateOf("")
        private set
    var number by mutableStateOf("")
        private set

    internal fun setGender(
        gender: Gender,
    ) = intent {
        reduce { state.copy(gender = gender) }
    }

    internal fun setName(name: String) {
        this.name = name.trim()
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = name,
                grade = grade,
                classRoom = classRoom,
                number = number,
            ),
        )
    }

    internal fun setGrade(grade: String) {
        this.grade = grade
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = name,
                grade = grade,
                classRoom = classRoom,
                number = number,
            ),
        )
    }

    internal fun setClass(classRoom: String) {
        this.classRoom = classRoom
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = name,
                grade = grade,
                classRoom = classRoom,
                number = number,
            ),
        )
    }

    internal fun setNumber(number: String) {
        this.number = number
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = name,
                grade = grade,
                classRoom = classRoom,
                number = number,
            ),
        )
    }

    internal fun setEmail(email: String) {
        this.email = email
        setEmailError(!Pattern.matches(Regex.EMAIL, email) || email.isBlank())
        intent {
            setSignUpButtonEnabled(email.isNotBlank() && verifyCode.isNotBlank() && !state.emailError && !state.verifyCodeError)
        }
    }

    private fun setEmailError(
        emailError: Boolean,
    ) = intent {
        reduce { state.copy(emailError = emailError) }
        setSendVerifyCodeButtonEnabled(!emailError)
    }

    internal fun setVerifyCode(verifyCode: String) {
        this.verifyCode = verifyCode
        setVerifyCodeError(false)
        intent {
            setSignUpButtonEnabled(email.isNotBlank() || verifyCode.isNotBlank() || !state.emailError || !state.verifyCodeError)
        }
    }

    private fun setVerifyCodeError(
        verifyCodeError: Boolean,
    ) = intent {
        reduce { state.copy(verifyCodeError = verifyCodeError) }
        setSignUpButtonEnabled(!verifyCodeError)
    }

    internal fun setPassword(password: String) {
        this.password = password
        setPasswordError(!Pattern.matches(Regex.PASSWORD, password))
    }

    private fun setPasswordError(
        passwordError: Boolean,
    ) = intent {
        reduce { state.copy(passwordError = passwordError) }
        setSignUpButtonEnabled(!passwordError)
    }

    internal fun setRepeatPassword(repeatPassword: String) {
        this.repeatPassword = repeatPassword
        setRepeatPasswordError(password != repeatPassword)
    }

    private fun setRepeatPasswordError(
        repeatPasswordError: Boolean,
    ) = intent {
        reduce { state.copy(repeatPasswordError = repeatPasswordError) }
        setSignUpButtonEnabled(!repeatPasswordError)
    }

    internal fun setSignUpButtonEnabled(
        signUpButtonEnabled: Boolean,
    ) = intent {
        reduce {
            state.copy(signUpButtonEnabled = signUpButtonEnabled)
        }
    }

    private fun setStudentNotFound(
        studentNotFound: Boolean,
    ) = intent {
        reduce {
            state.copy(studentNotFound = studentNotFound)
        }
    }

    private fun setSendVerifyCodeButtonEnabled(
        sendVerifyCodeButtonEnabled: Boolean,
    ) = intent {
        reduce { state.copy(sendVerifyCodeButtonEnabled = sendVerifyCodeButtonEnabled) }
    }

    private fun setAuthCodeEnabled() = intent {
        reduce { state.copy(authCodeEnabled = true) }
    }

    private fun checkAvailableValues(
        name: String,
        grade: String,
        classRoom: String,
        number: String,
    ): Boolean {
        setStudentNotFound(false)
        return name.isNotBlank() && grade.isNotBlank() && classRoom.isNotBlank() && number.isNotBlank()
    }

    internal fun checkStudentExists() = intent {
        viewModelScope.launch {
            checkStudentExistUseCase(
                checkStudentExistsParam = CheckStudentExistsParam(
                    gcn = returnGcn(
                        grade = grade,
                        `class` = classRoom,
                        number = number,
                    ),
                    name = name,
                ),
            ).onSuccess {
                postSideEffect(SignUpSideEffect.StudentInfo.CheckStudentExistsSuccess)
                setSignUpButtonEnabled(false)
            }.onFailure { throwable ->
                setSignUpButtonEnabled(signUpButtonEnabled = false)
                when (throwable) {
                    is NotFoundException -> {
                        postSideEffect(SignUpSideEffect.StudentInfo.CheckStudentExistsNotFound)
                        setStudentNotFound(studentNotFound = true)
                    }

                    else -> {
                        postSideEffect(
                            sideEffect = SignUpSideEffect.Exception(
                                message = getStringFromException(
                                    throwable = throwable,
                                ),
                            ),
                        )
                    }
                }
            }
        }
    }

    internal fun sendVerificationCode() = intent {
        viewModelScope.launch {
            sendVerificationCodeUseCase(
                sendVerificationCodeParam = SendVerificationCodeParam(
                    email = email,
                    authCodeType = AuthCodeType.SIGN_UP,
                ),
            ).onSuccess {
                setAuthCodeEnabled()
                postSideEffect(SignUpSideEffect.VerifyEmail.SendAuthCodeSuccess)
            }.onFailure { throwable ->
                when (throwable) {
                    is ConflictException -> {
                        postSideEffect(SignUpSideEffect.VerifyEmail.EmailConflict)
                    }

                    else -> {
                        postSideEffect(
                            SignUpSideEffect.Exception(
                                message = getStringFromException(
                                    throwable = throwable,
                                ),
                            ),
                        )
                    }
                }
            }
        }
    }

    internal fun verifyEmail() = intent {
        viewModelScope.launch {
            verifyEmailUseCase(
                verifyEmailParam = VerifyEmailParam(
                    email = email,
                    authCode = verifyCode,
                ),
            ).onSuccess {
                postSideEffect(SignUpSideEffect.VerifyEmail.VerifyEmailSuccess)
                setSignUpButtonEnabled(false)
            }.onFailure { throwable ->
                when (throwable) {
                    is UnAuthorizationException -> {
                        setVerifyCodeError(true)
                    }

                    is NullPointerException -> {
                        postSideEffect(SignUpSideEffect.VerifyEmail.VerifyEmailSuccess)
                        setSignUpButtonEnabled(false)
                    }

                    else -> {
                        postSideEffect(
                            sideEffect = SignUpSideEffect.Exception(
                                message = getStringFromException(
                                    throwable = throwable,
                                ),
                            ),
                        )
                    }
                }
            }
        }
    }

    internal fun signUp() = intent {
        viewModelScope.launch {
            if (
                !checkPassword(
                    password = password,
                    repeatPassword = repeatPassword,
                )
            ) {
                signUpUseCase(
                    signUpParam = SignUpParam(
                        email = email,
                        password = password,
                        grade = grade.toLong(),
                        name = name,
                        gender = state.gender,
                        classRoom = classRoom.toLong(),
                        number = number.toLong(),
                    ),
                ).onSuccess {
                    postSideEffect(
                        sideEffect = SignUpSideEffect.SetPassword.SignUpSuccess,
                    )
                }.onFailure { throwable ->
                    when (throwable) {
                        is ConflictException -> {
                            postSideEffect(
                                sideEffect = SignUpSideEffect.SetPassword.SignUpConflict,
                            )
                        }

                        else -> {
                            postSideEffect(
                                sideEffect = SignUpSideEffect.Exception(
                                    message = getStringFromException(
                                        throwable = throwable,
                                    ),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkPassword(
        password: String,
        repeatPassword: String,
    ): Boolean {
        val passwordError = !Pattern.matches(Regex.PASSWORD, password)
        val repeatPasswordError = password != repeatPassword

        setPasswordError(passwordError)
        setRepeatPasswordError(repeatPasswordError)

        return passwordError || repeatPasswordError
    }

    private fun returnGcn(
        grade: String,
        `class`: String,
        number: String,
    ) = Integer.parseInt("$grade$`class`${number.padStart(2, '0')}")
}

data class SignUpState(
    val emailError: Boolean = false,
    val verifyCodeError: Boolean = false,
    val passwordError: Boolean = false,
    val repeatPasswordError: Boolean = false,
    val gender: Gender = Gender.MAN,
    val studentNotFound: Boolean = false,
    val sendVerifyCodeButtonEnabled: Boolean = false,
    val authCodeEnabled: Boolean = false,
    val signUpButtonEnabled: Boolean = false,
) : State

sealed class SignUpSideEffect : SideEffect {
    sealed class StudentInfo {
        object CheckStudentExistsSuccess : SignUpSideEffect()
        object CheckStudentExistsNotFound : SignUpSideEffect()
    }

    sealed class VerifyEmail {
        object EmailConflict : SignUpSideEffect()
        object VerifyEmailSuccess : SignUpSideEffect()
        object SendAuthCodeSuccess : SignUpSideEffect()
    }

    sealed class SetPassword {
        object SignUpSuccess : SignUpSideEffect()
        object SignUpConflict : SignUpSideEffect()
    }

    class Exception(@StringRes val message: Int) : SignUpSideEffect()
}
