package team.retum.jobis_android.viewmodel.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.usecase.applications.FetchAppliedCompanyHistoriesUseCase
import team.retum.domain.usecase.applications.FetchStudentCountsUseCase
import team.retum.domain.usecase.user.SignOutUseCase
import team.retum.jobis_android.contract.home.HomeSideEffect
import team.retum.jobis_android.contract.home.HomeState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchStudentCountsUseCase: FetchStudentCountsUseCase,
    private val fetchAppliedCompanyHistoriesUseCase: FetchAppliedCompanyHistoriesUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    internal fun fetchTotalPassedStudentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentCountsUseCase().onSuccess {
                setStudentCounts(studentCountsEntity = it)
            }.onFailure {

            }
        }
    }

    internal fun fetchAppliedCompanyHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAppliedCompanyHistoriesUseCase().onSuccess {
                setAppliedCompanyHistories(appliedCompanyHistories = it)
            }.onFailure {

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
}