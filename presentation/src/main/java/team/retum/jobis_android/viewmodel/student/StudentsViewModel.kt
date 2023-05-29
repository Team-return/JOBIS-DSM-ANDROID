package team.retum.jobis_android.viewmodel.student

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.usecase.student.FetchStudentInformationUseCase
import team.retum.jobis_android.contract.StudentEvent
import team.retum.jobis_android.contract.StudentSideEffect
import team.retum.jobis_android.contract.StudentState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val fetchStudentInformationUseCase: FetchStudentInformationUseCase,
) : BaseViewModel<StudentState, StudentSideEffect>() {

    override val container = container<StudentState, StudentSideEffect>(StudentState())

    override fun sendEvent(event: Event) {
        when (event) {
            is StudentEvent.FetchStudentInformation -> {
                fetchStudentInformation()
            }
        }
    }

    private fun fetchStudentInformation() = intent {
        viewModelScope.launch(Dispatchers.IO){
            fetchStudentInformationUseCase().onSuccess {
                postSideEffect(
                    sideEffect = StudentSideEffect.SuccessFetchStudentInformation(
                        studentName = it.studentName,
                        studentGcn = it.studentGcn,
                        department = it.department,
                        profileImageUrl = it.profileImageUrl,
                    )
                )
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = StudentSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }


}