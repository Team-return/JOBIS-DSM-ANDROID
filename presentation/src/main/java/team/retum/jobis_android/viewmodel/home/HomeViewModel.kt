package team.retum.jobis_android.viewmodel.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.usecase.applications.FetchAppliedCompanyHistoriesUseCase
import team.retum.domain.usecase.applications.FetchStudentCountsUseCase
import team.retum.domain.usecase.student.FetchStudentInformationUseCase
import team.retum.jobis_android.contract.HomeSideEffect
import team.retum.jobis_android.contract.HomeState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchStudentCountsUseCase: FetchStudentCountsUseCase,
    private val fetchAppliedCompanyHistoriesUseCase: FetchAppliedCompanyHistoriesUseCase,
    private val fetchStudentInformationUseCase: FetchStudentInformationUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>() {

    override fun sendEvent(event: Event) {}

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    internal fun fetchTotalPassedStudentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentCountsUseCase().onSuccess {
                setStudentCounts(studentCountsEntity = it)
            }
        }
    }

    internal fun fetchAppliedCompanyHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAppliedCompanyHistoriesUseCase().onSuccess {
                setAppliedCompanyHistories(appliedCompanyHistories = it)
            }
        }
    }

    internal fun fetchStudentInformations() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentInformationUseCase().onSuccess {
                setStudentInformation(studentInformationEntity = it)
            }
        }
    }


    private fun setStudentCounts(
        studentCountsEntity: StudentCountsEntity,
    ) = intent {
        reduce {
            state.copy(
                studentCounts = studentCountsEntity,
            )
        }
    }

    private fun setAppliedCompanyHistories(
        appliedCompanyHistories: AppliedCompanyHistoriesEntity,
    ) = intent {
        reduce {
            state.copy(
                appliedCompanyHistories = appliedCompanyHistories.applications,
            )
        }
    }

    private fun setStudentInformation(
        studentInformationEntity: StudentInformationEntity,
    ) = intent {
        reduce {
            state.copy(
                studentInformation = studentInformationEntity,
            )
        }
    }
}