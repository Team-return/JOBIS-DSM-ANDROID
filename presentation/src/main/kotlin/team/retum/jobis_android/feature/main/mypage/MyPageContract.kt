package team.retum.jobis_android.feature.main.mypage

import team.retum.domain.enums.Department
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class MyPageState(
    val studentName: String = "",
    val studentGcn: String = "",
    val department: Department = Department.DEFAULT,
    val profileImageUrl: String = "",
) : State

internal sealed class MyPageSideEffect : SideEffect {
    object SuccessSignOut : MyPageSideEffect()
    object SuccessEditProfileImage : MyPageSideEffect()
    class Exception(val message: String) : MyPageSideEffect()
}
