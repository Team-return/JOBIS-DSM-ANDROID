package team.retum.jobis_android.viewmodel.changepassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.param.user.AuthCodeType
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.usecase.user.SendVerificationCodeUseCase
import team.retum.jobis_android.contract.ChangePasswordSideEffect
import team.retum.jobis_android.contract.ChangePasswordState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class ChangePasswordViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,

    ) : BaseViewModel<ChangePasswordState, ChangePasswordSideEffect>() {

    override val container = container<ChangePasswordState, ChangePasswordSideEffect>(
        ChangePasswordState()
    )

    override fun sendEvent(event: Event) {}

    internal fun sendVerificationCode() = intent{
        viewModelScope.launch(Dispatchers.IO){
            sendVerificationCodeUseCase(
                sendVerificationCodeParam = SendVerificationCodeParam(
                    email = state.email,
                    authCodeType = AuthCodeType.PASSWORD,
                    userName = "sefsef",
                )
            ).onSuccess {
                postSideEffect(sideEffect = ChangePasswordSideEffect.SuccessVerification)
            }.onFailure {
                setEmailErrorState(emailErrorState = true)
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
    ) = intent{
        reduce{
            state.copy(
                authCode = authCode,
            )
        }
    }

    internal fun setPassword(
        password: String,
    ) = intent{
        reduce{
            state.copy(
                password = password,
            )
        }
    }

    private fun setEmailErrorState(
        emailErrorState: Boolean,
    ) = intent{
        reduce {
            state.copy(
                emailErrorState = emailErrorState,
            )
        }
    }

}