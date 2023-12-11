package team.retum.jobis_android.feature.main.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.usecase.applications.FetchAppliedCompanyHistoriesUseCase
import team.retum.domain.usecase.applications.FetchStudentCountsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchStudentCountsUseCase: FetchStudentCountsUseCase,
    private val fetchAppliedCompanyHistoriesUseCase: FetchAppliedCompanyHistoriesUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        fetchTotalPassedStudentCount()
        fetchAppliedCompanyHistories()
    }

    private fun fetchTotalPassedStudentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentCountsUseCase().onSuccess {
                setStudentCounts(studentCountsEntity = it)
            }
        }
    }

    private fun fetchAppliedCompanyHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAppliedCompanyHistoriesUseCase().onSuccess {
                setAppliedCompanyHistories(appliedCompanyHistories = it)
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
