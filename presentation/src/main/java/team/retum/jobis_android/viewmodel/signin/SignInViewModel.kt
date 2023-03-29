package team.retum.jobis_android.viewmodel.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.OnServerException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.param.LoginParam
import team.retum.domain.usecase.LoginUseCase
import team.retum.jobis_android.contract.SignInSideEffect
import team.retum.jobis_android.contract.SignInState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ContainerHost<SignInState, SignInSideEffect>, ViewModel() {

    override val container = container<SignInState, SignInSideEffect>(SignInState())

    internal fun onEvent(
        event: SignInEvent,
    ){
        when(event){
            is SignInEvent.SetId -> {
                setUserId(
                    id = event.id,
                )
            }
            is SignInEvent.SetPassword -> {
                setPassword(
                    password = event.password,
                )
            }
        }
    }

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
                postSideEffect(SignInSideEffect.MoveToMain)
            }.onFailure {
                when (it) {
                    is UnAuthorizationException -> {
                        postSideEffect(SignInSideEffect.UnAuthorization)
                    }

                    is NotFoundException -> {
                        postSideEffect(SignInSideEffect.NotFound)
                    }

                    is OnServerException -> {
                        postSideEffect(SignInSideEffect.ServerException)
                    }
                }
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

    sealed class SignInEvent() {
        data class SetId(val id: String) : SignInEvent()
        data class SetPassword(val password: String) : SignInEvent()
    }
}