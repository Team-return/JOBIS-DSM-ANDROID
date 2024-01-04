package team.retum.jobis_android.feature.auth.signin

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
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInState, SignInSideEffect>() {

    override val container = container<SignInState, SignInSideEffect>(SignInState())

    internal fun postLogin() = intent {
        viewModelScope.launch {
            signInUseCase(
                param = SignInParam(
                    accountId = state.email,
                    password = state.password,
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

    internal fun setEmail(email: String) = intent {
        setSignInButtonEnabled()
        reduce {
            state.copy(
                email = email,
                emailError = false,
            )
        }
    }

    internal fun setPassword(password: String) = intent {
        setSignInButtonEnabled()
        reduce {
            state.copy(
                password = password,
                passwordError = false,
            )
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
