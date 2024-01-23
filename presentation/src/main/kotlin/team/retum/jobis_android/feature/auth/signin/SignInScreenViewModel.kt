package team.retum.jobis_android.feature.auth.signin

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
import team.retum.domain.exception.BadRequestException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.user.SignInParam
import team.retum.domain.usecase.user.SignInUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInState, SignInSideEffect>() {

    override val container = container<SignInState, SignInSideEffect>(SignInState())

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    internal fun postLogin() = intent {
        viewModelScope.launch {
            signInUseCase(
                param = SignInParam(
                    accountId = email,
                    password = password,
                    isAutoLogin = state.autoSignIn,
                ),
            ).onSuccess {
                postSideEffect(SignInSideEffect.MoveToMain)
            }.onFailure { throwable ->
                when (throwable) {
                    is UnAuthorizationException, is BadRequestException -> {
                        reduce {
                            state.copy(
                                passwordError = true,
                                signInButtonEnabled = false,
                            )
                        }
                    }

                    is NotFoundException -> {
                        reduce {
                            state.copy(
                                emailError = true,
                                signInButtonEnabled = false,
                            )
                        }
                    }

                    else -> {
                        postSideEffect(
                            SignInSideEffect.Exception(
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

    internal fun setEmail(email: String) {
        this.email = email
        setSignInButtonEnabled()
        intent {
            reduce {
                state.copy(emailError = false)
            }
        }
    }

    internal fun setPassword(password: String) {
        this.password = password
        setSignInButtonEnabled()
        intent {
            reduce {
                state.copy(passwordError = false)
            }
        }
    }

    internal fun setAutoSignIn(autoSignIn: Boolean) = intent {
        reduce { state.copy(autoSignIn = autoSignIn) }
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

data class SignInState(
    val autoSignIn: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val signInButtonEnabled: Boolean = false,
) : State

sealed class SignInSideEffect : SideEffect {
    object MoveToMain : SignInSideEffect()
    class Exception(@StringRes val message: Int) : SignInSideEffect()
}
