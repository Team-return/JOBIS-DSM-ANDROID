package team.retum.jobis_android.viewmodel.resetpassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.enums.AuthCodeType
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.usecase.student.ComparePasswordUseCase
import team.retum.domain.usecase.student.ResetPasswordUseCase
import team.retum.domain.usecase.user.SendVerificationCodeUseCase
import team.retum.domain.usecase.user.VerifyEmailUseCase
import team.retum.jobis_android.contract.resetpassword.ResetPasswordSideEffect
import team.retum.jobis_android.contract.resetpassword.ResetPasswordState
import team.retum.jobis_android.util.Regex
import team.retum.jobis_android.viewmodel.BaseViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
internal class ResetPasswordViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val comparePasswordUseCase: ComparePasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : BaseViewModel<ResetPasswordState, ResetPasswordSideEffect>() {

    override val container = container<ResetPasswordState, ResetPasswordSideEffect>(
        initialState = ResetPasswordState(),
    )

    internal fun sendVerificationCode() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            sendVerificationCodeUseCase(
                sendVerificationCodeParam = SendVerificationCodeParam(
                    email = state.email,
                    authCodeType = AuthCodeType.PASSWORD,
                )
            ).onSuccess {
                setSendAuthCodeState(sendAuthCodeErrorState = true)
            }.onFailure {
                setSendAuthCodeState(sendAuthCodeErrorState = false)
                setEmailErrorState(emailErrorState = true)
            }
        }
    }

    internal fun verifyEmail() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            verifyEmailUseCase(
                verifyEmailParam = VerifyEmailParam(
                    email = state.email,
                    authCode = state.authCode,
                )
            ).onSuccess {
                postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessVerification)
            }.onFailure {
                setAuthCodeState(authCodeErrorState = false)
            }
        }
    }

    internal fun comparePassword() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            comparePasswordUseCase(
                password = state.currentPassword,
            ).onSuccess {
                postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessVerification)
            }.onFailure {
                when (it) {
                    is UnAuthorizationException -> {
                        setComparePasswordErrorState(true)
                        postSideEffect(ResetPasswordSideEffect.PasswordMismatch)
                    }

                    else -> {
                        postSideEffect(
                            ResetPasswordSideEffect.Exception(
                                message = getStringFromException(it),
                            )
                        )
                    }
                }
            }
        }
    }

    internal fun resetPassword() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            resetPasswordUseCase(
                resetPasswordParam = ResetPasswordParam(
                    currentPassword = state.currentPassword,
                    newPassword = state.newPassword,
                )
            ).onSuccess {
                postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessResetPassword)
            }.onFailure {

            }
        }
    }

    private fun checkPasswordRepeat() {

    }

    internal fun setEmail(
        email: String,
    ) = intent {
        reduce {
            setEmailErrorState(emailErrorState = false)
            state.copy(
                email = email,
            )
        }
    }

    internal fun setAuthCode(
        authCode: String,
    ) = intent {
        reduce {
            state.copy(
                authCode = authCode,
            )
        }
    }

    internal fun setCurrentPassword(
        currentPassword: String,
    ) = intent {
        reduce {
            state.copy(
                currentPassword = currentPassword,
            )
        }
    }

    internal fun setNewPassword(
        newPassword: String,
    ) = intent {
        setPasswordFormatErrorState(
            passwordFormatErrorState = newPassword.isEmpty() || !Pattern.matches(
                Regex.PASSWORD,
                newPassword
            ),
        )
        reduce {
            state.copy(
                newPassword = newPassword,
            )
        }
    }

    internal fun setPasswordRepeat(
        passwordRepeat: String,
    ) = intent {
        reduce {
            setPasswordRepeatErrorState(
                passwordRepeatErrorState = passwordRepeat.isEmpty() || state.newPassword != passwordRepeat,
            )
            state.copy(
                passwordRepeat = passwordRepeat,
            )
        }
    }

    private fun setEmailErrorState(
        emailErrorState: Boolean,
    ) = intent {
        reduce {
            state.copy(
                emailErrorState = emailErrorState,
            )
        }
    }

    private fun setSendAuthCodeState(
        sendAuthCodeErrorState: Boolean,
    ) = intent {
        reduce {
            state.copy(
                sendAuthCodeState = sendAuthCodeErrorState,
            )
        }
    }

    private fun setAuthCodeState(
        authCodeErrorState: Boolean,
    ) = intent {
        reduce {
            state.copy(
                authCodeErrorState = authCodeErrorState,
            )
        }
    }

    internal fun setComparePasswordErrorState(
        comparePasswordErrorState: Boolean,
    ) = intent {
        reduce {
            state.copy(
                comparePasswordErrorState = comparePasswordErrorState,
            )
        }
    }

    private fun setPasswordRepeatErrorState(
        passwordRepeatErrorState: Boolean,
    ) = intent {
        reduce {
            state.copy(
                passwordRepeatErrorState = passwordRepeatErrorState,
            )
        }
    }

    private fun setPasswordFormatErrorState(
        passwordFormatErrorState: Boolean,
    ) = intent {
        reduce {
            state.copy(
                passwordFormatErrorState = passwordFormatErrorState,
            )
        }
    }
}