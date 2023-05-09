package team.retum.jobis_android.viewmodel.signup

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
import team.retum.domain.param.AuthCodeType
import team.retum.domain.param.CheckStudentExistsParam
import team.retum.domain.param.SendVerificationCodeParam
import team.retum.domain.param.Sex
import team.retum.domain.param.SignUpParam
import team.retum.domain.param.VerifyEmailParam
import team.retum.domain.usecase.CheckStudentExistUseCase
import team.retum.domain.usecase.SendVerificationCodeUseCase
import team.retum.domain.usecase.SignUpUseCase
import team.retum.domain.usecase.VerifyEmailUseCase
import team.retum.jobis_android.contract.SignUpEvent
import team.retum.jobis_android.contract.SignUpSideEffect
import team.retum.jobis_android.contract.SignUpState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkStudentExistUseCase: CheckStudentExistUseCase,
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect>() {

    override val container = container<SignUpState, SignUpSideEffect>(SignUpState())

    private val state = container.stateFlow.value

    override fun sendEvent(event: Event) {
        when (event) {
            is SignUpEvent.SetSex -> setSex(event.sex)
            is SignUpEvent.SetName -> setName(event.name)
            is SignUpEvent.SetGrade -> setGrade(event.grade)
            is SignUpEvent.SetClass -> setClass(event.`class`)
            is SignUpEvent.SetNumber -> setNumber(event.number)
            is SignUpEvent.SetEmail -> setEmail(event.email)
            is SignUpEvent.SetVerifyCode -> setVerifyCode(event.verifyCode)
            is SignUpEvent.SetPassword -> setPassword(event.password)
            is SignUpEvent.SetRepeatPassword -> setRepeatPassword(event.repeatPassword)
            is SignUpEvent.CheckStudentExists -> checkStudentExists()
            is SignUpEvent.SendVerificationCode -> sendVerificationCode(
                email = event.email,
                authCodeType = event.authCodeType,
                userName = event.userName,
            )

            is SignUpEvent.VerifyEmail -> verifyEmail()
        }
    }

    private fun setSex(
        sex: Sex,
    ) = intent {
        reduce { state.copy(gender = sex) }
    }

    private fun setName(
        name: String
    ) = intent {
        reduce { state.copy(name = name) }
    }

    private fun setGrade(
        grade: Int,
    ) = intent {
        reduce { state.copy(grade = grade) }
    }

    private fun setClass(
        `class`: Int,
    ) = intent {
        reduce { state.copy(`class` = `class`) }
    }

    private fun setNumber(
        number: Int,
    ) = intent {
        reduce { state.copy(number = number) }
    }

    private fun setEmail(
        email: String,
    ) = intent {
        reduce { state.copy(email = email) }
    }

    private fun setVerifyCode(
        verifyCode: String,
    ) = intent {
        reduce { state.copy(authCode = verifyCode) }
    }

    private fun setPassword(
        password: String,
    ) = intent {
        reduce { state.copy(password = password) }
    }

    private fun setRepeatPassword(
        repeatPassword: String,
    ) = intent {
        reduce { state.copy(repeatPassword = repeatPassword) }
    }

    private fun checkStudentExists() = intent {
        viewModelScope.launch {
            checkStudentExistUseCase(
                checkStudentExistsParam = CheckStudentExistsParam(
                    gcn = returnGcn(
                        grade = state.grade,
                        `class` = state.`class`,
                        number = state.number,
                    ),
                    name = container.stateFlow.value.name,
                )
            ).onSuccess {
                postSideEffect(
                    sideEffect = SignUpSideEffect.CheckStudentExistsSuccess,
                )
            }.onFailure { throwable ->
                when (throwable) {
                    is NotFoundException -> {
                        postSideEffect(
                            sideEffect = SignUpSideEffect.CheckStudentExistsNotFound,
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

    private fun sendVerificationCode(
        email: String,
        authCodeType: AuthCodeType,
        userName: String,
    ) = intent {
        viewModelScope.launch {
            sendVerificationCodeUseCase(
                sendVerificationCodeParam = SendVerificationCodeParam(
                    email = email,
                    authCodeType = authCodeType,
                    userName = userName,
                )
            ).onSuccess {
                postSideEffect(
                    sideEffect = SignUpSideEffect.SendVerificationCodeSuccess,
                )
            }.onFailure { throwable ->
                when (throwable) {
                    is ConflictException -> {
                        postSideEffect(SignUpSideEffect.EmailConflict)
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

    private fun verifyEmail() = intent {
        viewModelScope.launch {
            verifyEmailUseCase(
                verifyEmailParam = VerifyEmailParam(
                    email = state.email,
                    authCode = state.authCode,
                )
            ).onSuccess {
                postSideEffect(
                    sideEffect = SignUpSideEffect.VerifyEmailSuccess,
                )
            }.onFailure { throwable ->
                when (throwable) {
                    is UnAuthorizationException -> {
                        postSideEffect(
                            sideEffect = SignUpSideEffect.VerifyEmailUnAuthorized,
                        )
                    }

                    is NotFoundException -> {
                        postSideEffect(
                            sideEffect = SignUpSideEffect.VerifyEmailNotFound,
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

    private fun signUp() = intent {
        viewModelScope.launch {
            signUpUseCase(
                signUpParam = SignUpParam(
                    email = state.email,
                    phoneNumber = state.phoneNumber,
                    password = state.password,
                    grade = state.grade,
                    name = state.name,
                    gender = state.gender,
                    classRoom = state.`class`,
                    number = state.number,
                )
            ).onSuccess {
                postSideEffect(
                    sideEffect = SignUpSideEffect.SignUpSuccess,
                )
            }.onFailure { throwable ->
                when(throwable){
                    is ConflictException -> {
                        postSideEffect(
                            sideEffect = SignUpSideEffect.SignUpConflict,
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

    private fun returnGcn(
        grade: Int,
        `class`: Int,
        number: Int,
    ) = Integer.parseInt("$grade$`class`${number.toString().padStart(2, '0')}")
}