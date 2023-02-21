package com.jobis.jobis_android.viewmodel.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobis.domain.exception.BadRequestException
import com.jobis.domain.exception.NotFoundException
import com.jobis.domain.exception.OnServerException
import com.jobis.domain.exception.UnAuthorizationException
import com.jobis.domain.param.LoginParam
import com.jobis.domain.usecase.LoginUseCase
import com.jobis.jobis_android.contract.LoginSideEffect
import com.jobis.jobis_android.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ContainerHost<LoginState, LoginSideEffect>, ViewModel() {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    internal fun postLogin() = intent {
        viewModelScope.launch {
            kotlin.runCatching {
                loginUseCase.execute(
                    data = LoginParam(
                        accountId = state.accountId,
                        password = state.password,
                        isAutoLogin = state.isAutoLogin,
                    )
                )
            }.onSuccess {
                postSideEffect(LoginSideEffect.MoveToMain)
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
                    is BadRequestException -> {
                        postSideEffect(LoginSideEffect.BadRequest)
                    }
                }
            }
        }
    }

    internal fun setUserId(
        id: String,
    ) = intent{
        reduce { state.copy(accountId = id) }
    }

    internal fun setPassword(
        password: String,
    ) = intent{
        reduce { state.copy(password = password)}
    }

    internal fun setAutoLogin(
        isAutoLogin: Boolean,
    ) = intent {
        reduce { state.copy(isAutoLogin = isAutoLogin) }
    }
}