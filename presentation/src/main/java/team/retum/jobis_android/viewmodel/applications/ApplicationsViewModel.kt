package team.retum.jobis_android.viewmodel.applications

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.usecase.FetchTotalPassedStudentCountUseCase
import team.retum.jobis_android.contract.ApplicationsEvent
import team.retum.jobis_android.contract.ApplicationsSideEffect
import team.retum.jobis_android.contract.ApplicationsState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class ApplicationsViewModel @Inject constructor(
    private val fetchTotalPassedStudentCountUseCase: FetchTotalPassedStudentCountUseCase,
): BaseViewModel<ApplicationsState, ApplicationsSideEffect>() {

    override val container = container<ApplicationsState, ApplicationsSideEffect>(ApplicationsState())

    override fun sendEvent(event: Event) {
        when(event){
            is ApplicationsEvent.FetchTotalPassedStudentCount -> {

            }
        }
    }

    private fun fetchTotalPassedStudentCount() = intent {
        viewModelScope.launch(Dispatchers.IO){
            fetchTotalPassedStudentCountUseCase().onSuccess {
                postSideEffect(
                    sideEffect = ApplicationsSideEffect.SuccessFetchTotalPassedStudentCount(
                        totalStudentCount = it.totalStudentCount,
                        passCount = it.passCount,
                        approvedCount = it.approvedCount,
                    )
                )
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = ApplicationsSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }
}