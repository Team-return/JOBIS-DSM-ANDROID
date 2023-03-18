package team.retum.jobis_android.viewmodel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.exception.OnServerException
import team.retum.domain.usecase.LoginUseCase
import team.retum.domain.usecase.SplashUseCase
import team.retum.jobis_android.contract.LoginSideEffect
import team.retum.jobis_android.contract.LoginState
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val splashUseCase: SplashUseCase,
): ContainerHost<LoginState, LoginSideEffect>, ViewModel() {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    internal fun postLogin() = intent {
        viewModelScope.launch {
            kotlin.runCatching {
                loginUseCase.execute(
                    data = splashUseCase.execute(Unit)
                )
            }.onSuccess {
                postSideEffect(LoginSideEffect.MoveToMain)
            }.onFailure {
                when (it) {
                    is OnServerException -> {
                        postSideEffect(LoginSideEffect.OnServerError)
                    }
                    else -> {
                        postSideEffect(LoginSideEffect.MoveToLogin)
                    }
                }
            }
        }
    }
}