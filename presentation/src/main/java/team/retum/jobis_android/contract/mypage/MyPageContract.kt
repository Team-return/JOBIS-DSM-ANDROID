package team.retum.jobis_android.contract.mypage

import team.retum.domain.entity.student.Department
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class MyPageState(
    val studentName: String = "",
    val studentGcn: String = "",
    val department: Department = Department.DEFAULT,
    val profileImageUrl: String = "https://jobis-file.s3.ap-northeast-2.amazonaws.com/EXTENSION_FILE/default_profile.png",
    val editProfileImageUrl: String = ""
) : State

internal sealed class MyPageSideEffect : SideEffect {
    class Exception(val message: String) : MyPageSideEffect()
}