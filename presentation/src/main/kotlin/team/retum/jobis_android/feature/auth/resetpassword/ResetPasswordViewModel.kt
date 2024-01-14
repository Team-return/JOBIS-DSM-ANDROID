package team.retum.jobis_android.feature.auth.resetpassword

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.enums.AuthCodeType
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.students.ChangePasswordParam
import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.usecase.student.ChangePasswordUseCase
import team.retum.domain.usecase.student.ComparePasswordUseCase
import team.retum.domain.usecase.student.ResetPasswordUseCase
import team.retum.domain.usecase.user.SendVerificationCodeUseCase
import team.retum.domain.usecase.user.VerifyEmailUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.Regex
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
internal class ResetPasswordViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val comparePasswordUseCase: ComparePasswordUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
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
                ),
            ).onSuccess {
                setSendAuthCodeState(sendAuthCodeErrorState = true)
                postSideEffect(ResetPasswordSideEffect.ClearFocus)
            }.onFailure {
                setSendAuthCodeState(sendAuthCodeErrorState = false)
                when (it) {
                    is NotFoundException -> {
                        setEmailErrorState(emailErrorState = true)
                    }

                    else -> {
                        postSideEffect(ResetPasswordSideEffect.Exception(getStringFromException(it)))
                    }
                }
            }
        }
    }

    internal fun verifyEmail() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            verifyEmailUseCase(
                verifyEmailParam = VerifyEmailParam(
                    email = state.email,
                    authCode = state.authCode,
                ),
            ).onSuccess {
                postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessVerification)
            }.onFailure {
                when (it) {
                    is UnAuthorizationException -> {
                        setAuthCodeErrorState()
                    }

                    is KotlinNullPointerException -> {
                        postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessVerification)
                    }
                }
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
                            ),
                        )
                    }
                }
            }
        }
    }

    internal fun changePassword() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            changePasswordUseCase(
                changePasswordParam = ChangePasswordParam(
                    currentPassword = state.currentPassword,
                    newPassword = state.newPassword,
                ),
            ).onSuccess {
                postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessChangePassword)
            }.onFailure {
                postSideEffect(ResetPasswordSideEffect.Exception(getStringFromException(it)))
            }
        }
    }

    internal fun resetPassword() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            resetPasswordUseCase(
                resetPasswordParam = ResetPasswordParam(
                    email = state.email,
                    password = state.newPassword,
                ),
            ).onSuccess {
                postSideEffect(ResetPasswordSideEffect.SuccessResetPassword)
            }.onFailure {
                postSideEffect(ResetPasswordSideEffect.Exception(getStringFromException(it)))
            }
        }
    }

    internal fun setEmail(
        email: String,
    ) = intent {
        reduce {
            setEmailErrorState(emailErrorState = false)
            state.copy(email = email)
        }
    }

    internal fun setAuthCode(
        authCode: String,
    ) = intent {
        authCode.take(6)
        if (authCode.length == 6) {
            postSideEffect(ResetPasswordSideEffect.ClearFocus)
        }
        reduce {
            state.copy(authCode = authCode)
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
        reduce {
            with(state) {
                copy(
                    newPassword = newPassword,
                    passwordFormatErrorState = !Regex(Regex.PASSWORD).matches(newPassword),
                    passwordRepeatErrorState = newPassword != passwordRepeat && passwordRepeat.isNotBlank(),
                )
            }
        }
        setButtonEnabled()
    }

    internal fun setPasswordRepeat(
        passwordRepeat: String,
    ) = intent {
        reduce {
            with(state) {
                copy(
                    passwordRepeat = passwordRepeat,
                    passwordRepeatErrorState = newPassword != passwordRepeat,
                )
            }
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = intent {
        reduce {
            with(state) {
                val isBlank = newPassword.isBlank() || passwordRepeat.isBlank()
                val isError = passwordFormatErrorState || passwordRepeatErrorState
                state.copy(buttonEnabled = !(isBlank || isError))
            }
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

    private fun setAuthCodeErrorState() = intent {
        reduce {
            state.copy(authCodeErrorState = true)
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
}

data class ResetPasswordState(
    val email: String = "",
    val authCode: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val passwordRepeat: String = "",
    val emailErrorState: Boolean = false,
    val authCodeErrorState: Boolean = false,
    val sendAuthCodeState: Boolean = false,
    val passwordFormatErrorState: Boolean = false,
    val passwordRepeatErrorState: Boolean = false,
    val comparePasswordErrorState: Boolean = false,
    val buttonEnabled: Boolean = false,
) : State

sealed class ResetPasswordSideEffect : SideEffect {
    object SuccessVerification : ResetPasswordSideEffect()
    object SuccessChangePassword : ResetPasswordSideEffect()
    object SuccessResetPassword : ResetPasswordSideEffect()
    object PasswordMismatch : ResetPasswordSideEffect()
    object ClearFocus : ResetPasswordSideEffect()
    class Exception(@StringRes val message: Int) : ResetPasswordSideEffect()
}
