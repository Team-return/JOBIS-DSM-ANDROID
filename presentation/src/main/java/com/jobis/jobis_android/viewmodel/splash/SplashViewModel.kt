package com.jobis.jobis_android.viewmodel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobis.domain.exception.NotFoundException
import com.jobis.domain.exception.OnServerException
import com.jobis.domain.exception.UnAuthorizationException
import com.jobis.domain.usecase.LoginUseCase
import com.jobis.domain.usecase.SplashUseCase
import com.jobis.jobis_android.contract.LoginSideEffect
import com.jobis.jobis_android.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
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
                postSideEffect(LoginSideEffect.Success)
            }.onFailure {
                when (it) {
                    is UnAuthorizationException -> {
                        postSideEffect(LoginSideEffect.UnAuthorization)
                    }
                    is NotFoundException -> {
                        postSideEffect(LoginSideEffect.NotFound)
                    }
                    is OnServerException -> {
                        postSideEffect(LoginSideEffect.OnServerError)
                    }
                }
            }
        }
    }
}