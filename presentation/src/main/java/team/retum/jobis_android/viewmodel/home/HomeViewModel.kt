package team.retum.jobis_android.viewmodel.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.UserApplyCompaniesEntity
import team.retum.domain.usecase.FetchUserApplyCompaniesUseCase
import team.retum.jobis_android.contract.HomeEvent
import team.retum.jobis_android.contract.HomeSideEffect
import team.retum.jobis_android.contract.HomeState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchUserApplyCompaniesUseCase: FetchUserApplyCompaniesUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    override fun sendEvent(event: Event) {
        when (event) {
            is HomeEvent.FetchUserApplyCompanies -> {
                fetchUserApplyCompanies()
            }
        }
    }

    private fun fetchUserApplyCompanies() = intent {
        viewModelScope.launch {
            fetchUserApplyCompaniesUseCase().onSuccess {
                setHomeState(
                    mainPageInformations = it,
                )
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = HomeSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }

    private fun setHomeState(
        mainPageInformations: UserApplyCompaniesEntity,
    ) = intent {
        with(mainPageInformations) {
            reduce {
                state.copy(
                    name = name,
                    gcn = gcn,
                    applyCompanies = applyCompanies,
                    totalStudentCont = totalStudentCount,
                    passCount = passCount,
                    approvedCount = approvedCount,
                )
            }
        }
    }
}