package team.retum.jobis_android.feature.main.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.applications.AppliedCompanyEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.enums.Department
import team.retum.domain.usecase.applications.FetchAppliedCompanyHistoriesUseCase
import team.retum.domain.usecase.applications.FetchStudentCountsUseCase
import team.retum.domain.usecase.student.FetchStudentInformationUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val fetchStudentCountsUseCase: FetchStudentCountsUseCase,
    private val fetchAppliedCompanyHistoriesUseCase: FetchAppliedCompanyHistoriesUseCase,
    private val fetchStudentInformationUseCase: FetchStudentInformationUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        fetchTotalPassedStudentCount()
        fetchAppliedCompanyHistories()
        fetchStudentInformation()
    }

    private fun fetchTotalPassedStudentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentCountsUseCase().onSuccess {
                reduce { state.copy(studentCounts = it) }
            }
        }
    }

    private fun fetchAppliedCompanyHistories() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAppliedCompanyHistoriesUseCase().onSuccess {
                reduce { state.copy(appliedCompanies = it.applications) }
            }
        }
    }

    private fun fetchStudentInformation() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentInformationUseCase().onSuccess {
                val profileImageUrl = JobisUrl.imageUrl + it.profileImageUrl
                reduce { state.copy(studentInformation = it.copy(profileImageUrl = profileImageUrl)) }
            }
        }
    }
}

data class HomeState(
    val studentCounts: StudentCountsEntity = StudentCountsEntity(
        totalStudentCount = 0L,
        passCount = 0L,
        approvedCount = 0L,
    ),
    val appliedCompanies: List<AppliedCompanyEntity> = emptyList(),
    val studentInformation: StudentInformationEntity = StudentInformationEntity(
        studentName = "",
        studentGcn = "",
        department = Department.DEFAULT,
        profileImageUrl = "",
    ),
) : State

sealed class HomeSideEffect : SideEffect
