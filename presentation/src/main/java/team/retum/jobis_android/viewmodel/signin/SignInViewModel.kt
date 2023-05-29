package team.retum.jobis_android.viewmodel.signin

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.user.SignInParam
import team.retum.domain.usecase.user.SignInUseCase
import team.retum.jobis_android.contract.SignInEvent
import team.retum.jobis_android.contract.SignInSideEffect
import team.retum.jobis_android.contract.SignInState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel

import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInState, SignInSideEffect>() {

    override val container = container<SignInState, SignInSideEffect>(SignInState())

    override fun sendEvent(
        event: Event,
    ) {
        when (event) {
            is SignInEvent.SetEmail -> {
                setUserId(
                    id = event.email,
                )
            }

            is SignInEvent.SetPassword -> {
                setPassword(
                    password = event.password,
                )
            }

            is SignInEvent.PostLogin -> {
                postLogin()
            }
        }
    }

    private fun postLogin() = intent {
        viewModelScope.launch {
            kotlin.runCatching {
                signInUseCase.execute(
                    data = SignInParam(
                        accountId = state.accountId,
                        password = state.password,
                        isAutoLogin = state.isAutoLogin,
                    )
                )
            }.onSuccess {
                postSideEffect(SignInSideEffect.MoveToMain)
            }.onFailure { throwable ->
                postSignInErrorEffect(
                    throwable = throwable,
                )
            }
        }
    }

    private fun postSignInErrorEffect(
        throwable: Throwable,
    ) = intent {
        when (throwable) {

            is UnAuthorizationException -> {
                postSideEffect(SignInSideEffect.UnAuthorization)
            }

            is NotFoundException -> {
                postSideEffect(SignInSideEffect.NotFound)
            }

            else -> {
                postSideEffect(
                    SignInSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }

    private fun setUserId(
        id: String,
    ) = intent {
        reduce { state.copy(accountId = id) }
    }

    private fun setPassword(
        password: String,
    ) = intent {
        reduce { state.copy(password = password) }
    }
}