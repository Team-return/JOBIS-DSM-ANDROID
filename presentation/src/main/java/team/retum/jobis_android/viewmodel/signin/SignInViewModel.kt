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

    override fun sendEvent(event: Event) {}

    internal fun postLogin() = intent {
        viewModelScope.launch {
            signInUseCase(
                param = SignInParam(
                    accountId = state.email,
                    password = state.password,
                    isAutoLogin = state.autoSignIn,
                )
            ).onSuccess {
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

    internal fun setEmail(
        email: String,
    ) = intent {
        setSignInButtonEnabled()
        reduce { state.copy(email = email) }
    }

    internal fun setPassword(
        password: String,
    ) = intent {
        setSignInButtonEnabled()
        reduce { state.copy(password = password) }
    }

    internal fun setAutoSignIn(
        autoSignIn: Boolean,
    ) = intent {
        reduce { state.copy(autoSignIn = autoSignIn) }
    }

    internal fun setEmailError(
        emailError: Boolean,
    ) = intent {
        setSignInButtonEnabled()
        reduce { state.copy(emailError = emailError) }
    }

    internal fun setPasswordError(
        passwordError: Boolean,
    ) = intent {
        setSignInButtonEnabled()
        reduce { state.copy(passwordError = passwordError) }
    }

    private fun setSignInButtonEnabled() = intent {
        with(state) {
            reduce {
                copy(
                    signInButtonEnabled = email.isNotBlank() && password.isNotBlank() && !emailError && !passwordError,
                )
            }
        }
    }
}