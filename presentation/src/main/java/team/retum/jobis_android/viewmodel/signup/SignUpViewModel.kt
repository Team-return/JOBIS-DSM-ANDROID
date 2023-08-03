package team.retum.jobis_android.viewmodel.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.exception.ConflictException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.user.AuthCodeType
import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.Sex
import team.retum.domain.param.user.SignUpParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.usecase.user.CheckStudentExistUseCase
import team.retum.domain.usecase.user.SendVerificationCodeUseCase
import team.retum.domain.usecase.user.SignUpUseCase
import team.retum.domain.usecase.user.VerifyEmailUseCase
import team.retum.jobis_android.contract.SignUpSideEffect
import team.retum.jobis_android.contract.SignUpState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import java.util.regex.Pattern
import javax.inject.Inject

private const val passwordRegex =
    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#\$%^&*()+|=])[A-Za-z\\d~!@#\$%^&*()+|=]{8,16}\$"

private const val emailRegex = "^.+@dsm.hs.kr$"


@HiltViewModel
class SignUpViewModel @Inject constructor(
    internal val checkStudentExistUseCase: CheckStudentExistUseCase,
    internal val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    internal val verifyEmailUseCase: VerifyEmailUseCase,
    internal val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect>() {

    override val container = container<SignUpState, SignUpSideEffect>(SignUpState())

    internal val state = container.stateFlow.value

    override fun sendEvent(event: Event) {}

    internal fun setSex(
        sex: Sex,
    ) = intent {
        reduce { state.copy(sex = sex) }
    }

    internal fun setName(
        name: String
    ) = intent {
        reduce { state.copy(name = name.trim()) }
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = name,
                grade = state.grade,
                `class` = state.classRoom,
                number = state.number,
            )
        )
    }

    internal fun setGrade(
        grade: String,
    ) = intent {
        reduce { state.copy(grade = grade) }
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = state.name,
                grade = state.grade,
                `class` = state.classRoom,
                number = state.number,
            )
        )
    }

    internal fun setClass(
        `class`: String,
    ) = intent {
        reduce { state.copy(classRoom = `class`) }
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = state.name,
                grade = state.grade,
                `class` = `class`,
                number = state.number,
            )
        )
    }

    internal fun setNumber(
        number: String,
    ) = intent {
        reduce { state.copy(number = number) }
        setSignUpButtonEnabled(
            checkAvailableValues(
                name = state.name,
                grade = state.grade,
                `class` = state.classRoom,
                number = number,
            )
        )
    }

    internal fun setEmail(
        email: String,
    ) = intent {
        reduce { state.copy(email = email) }
        setEmailError(!Pattern.matches(emailRegex, email) || email.isBlank())
        setSignUpButtonEnabled(email.isNotBlank() && state.verifyCode.isNotBlank() && !state.emailError && !state.verifyCodeError)
    }

    private fun setEmailError(
        emailError: Boolean,
    ) = intent {
        reduce { state.copy(emailError = emailError) }
        setSendVerifyCodeButtonEnabled(!emailError)
    }

    internal fun setVerifyCode(
        verifyCode: String,
    ) = intent {
        reduce { state.copy(verifyCode = verifyCode) }
        setVerifyCodeError(false)
        setSignUpButtonEnabled(state.email.isNotBlank() || verifyCode.isNotBlank() || !state.emailError || !state.verifyCodeError)
    }

    private fun setVerifyCodeError(
        verifyCodeError: Boolean,
    ) = intent {
        reduce { state.copy(verifyCodeError = verifyCodeError) }
        setSignUpButtonEnabled(!verifyCodeError)
    }

    internal fun setPassword(
        password: String,
    ) = intent {
        reduce { state.copy(password = password) }
        setPasswordError(!Pattern.matches(passwordRegex, password))
    }

    private fun setPasswordError(
        passwordError: Boolean,
    ) = intent {
        reduce { state.copy(passwordError = passwordError) }
        setSignUpButtonEnabled(!passwordError)
    }

    internal fun setRepeatPassword(
        repeatPassword: String,
    ) = intent {
        reduce { state.copy(repeatPassword = repeatPassword) }
        setRepeatPasswordError(state.password != repeatPassword)
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

    private fun setAuthCodeEnabled(
        authCodeEnabled: Boolean,
    ) = intent {
        reduce { state.copy(authCodeEnabled = authCodeEnabled) }
    }

    private fun checkAvailableValues(
        name: String,
        grade: String,
        `class`: String,
        number: String,
    ): Boolean {
        setStudentNotFound(false)
        return name.isNotBlank() && grade.isNotBlank() && `class`.isNotBlank() && number.isNotBlank()
    }

    internal fun checkStudentExists() = intent {
        viewModelScope.launch {
            checkStudentExistUseCase(
                checkStudentExistsParam = CheckStudentExistsParam(
                    gcn = returnGcn(
                        grade = state.grade,
                        `class` = state.classRoom,
                        number = state.number,
                    ),
                    name = container.stateFlow.value.name,
                )
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
                                )
                            )
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
                    email = state.email,
                    authCodeType = AuthCodeType.SIGN_UP,
                )
            ).onSuccess {
                setAuthCodeEnabled(true)
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
                                )
                            )
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
                    email = state.email,
                    authCode = state.verifyCode,
                )
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
                                )
                            )
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
                    password = state.password,
                    repeatPassword = state.repeatPassword,
                )
            ) {
                signUpUseCase(
                    signUpParam = SignUpParam(
                        email = state.email,
                        password = state.password,
                        grade = state.grade.toInt(),
                        name = state.name,
                        gender = state.sex,
                        classRoom = state.classRoom.toInt(),
                        number = state.number.toInt(),
                    )
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
                                    )
                                )
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

        val passwordError = !Pattern.matches(passwordRegex, password)
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