package com.jobis.jobis_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobis.domain.exception.NotFoundException
import com.jobis.domain.exception.UnAuthorizationException
import com.jobis.domain.param.LoginParam
import com.jobis.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _loginEvent = MutableSharedFlow<Event>()
    val loginEvent
        get() = _loginEvent.asSharedFlow()

    internal fun postLogin(
        id: String,
        password: String,
    ) {
        viewModelScope.launch {
            runCatching {
                loginUseCase.execute(
                    LoginParam(
                        accountId = id,
                        password = password,
                    )
                )
            }.onSuccess {
                _loginEvent.emit(Event.MoveToMainActivity)
            }.onFailure {
                println(it.toString())
                when (it) {
                    is UnAuthorizationException -> _loginEvent.emit(Event.UnAuthorization)
                    is NotFoundException -> _loginEvent.emit(Event.NotFound)
                }
            }
        }
    }

    sealed class Event {
        object MoveToMainActivity : Event()
        object UnAuthorization : Event()
        object NotFound : Event()
    }
}