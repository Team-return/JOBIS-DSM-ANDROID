package team.retum.jobis_android.viewmodel.resetpassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.exception.NotFoundException
import team.retum.domain.param.user.AuthCodeType
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.usecase.user.SendVerificationCodeUseCase
import team.retum.domain.usecase.user.VerifyEmailUseCase
import team.retum.jobis_android.contract.ResetPasswordSideEffect
import team.retum.jobis_android.contract.ResetPasswordState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class ResetPasswordViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
) : BaseViewModel<ResetPasswordState, ResetPasswordSideEffect>() {

    override val container = container<ResetPasswordState, ResetPasswordSideEffect>(
        ResetPasswordState()
    )

    override fun sendEvent(event: Event) {}

    internal fun sendVerificationCode() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            sendVerificationCodeUseCase(
                sendVerificationCodeParam = SendVerificationCodeParam(
                    email = state.email,
                    authCodeType = AuthCodeType.PASSWORD,
                    userName = "테스트",
                )
            ).onSuccess {
                setSendAuthCodeState(sendAuthCodeErrorState = true)
            }.onFailure {
                when (it) {
                    is NotFoundException -> {
                        setEmailErrorState(emailErrorState = true)
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
                )
            ).onSuccess {
                postSideEffect(sideEffect = ResetPasswordSideEffect.SuccessVerification)
            }.onFailure {
                setAuthCodeState(authCodeErrorState = false)
            }
        }
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

    internal fun setPassword(
        password: String,
    ) = intent {
        reduce {
            state.copy(
                password = password,
            )
        }
    }

    internal fun setPasswordRepeat(
        passwordRepeat: String,
    ) = intent {
        reduce {
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
}