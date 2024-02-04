package team.retum.jobis_android.feature.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.usecase.user.FetchAutoSignInOptionUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchAutoSignInOptionUseCase: FetchAutoSignInOptionUseCase,
) : BaseViewModel<MainState, MainSideEffect>() {

    override val container = container<MainState, MainSideEffect>(MainState())

    init {
        fetchAutoSignInOption()
    }

    private fun fetchAutoSignInOption() {
        runBlocking {
            fetchAutoSignInOptionUseCase().onSuccess {
                setAutoSignInOptionState(it)
            }
        }
    }

    private fun setAutoSignInOptionState(
        autoSignInOption: Boolean,
    ) = intent {
        reduce {
            state.copy(
                autoSignInOption = autoSignInOption,
            )
        }
    }
}

data class MainState(
    val autoSignInOption: Boolean = false,
) : State

sealed class MainSideEffect : SideEffect
