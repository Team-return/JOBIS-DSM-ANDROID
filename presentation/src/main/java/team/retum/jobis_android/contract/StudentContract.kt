package team.retum.jobis_android.contract

import team.retum.domain.entity.student.Department
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

sealed class StudentSideEffect : SideEffect {
    class SuccessFetchStudentInformation(
        val studentName: String,
        val studentGcn: String,
        val department: Department,
        val profileImageUrl: String,
    ) : StudentSideEffect()

    class Exception(
        val message: String,
    ): StudentSideEffect()
}

data class StudentState(
    val temp: String = "",
) : State

sealed class StudentEvent : Event {
    object FetchStudentInformation : StudentEvent()
}