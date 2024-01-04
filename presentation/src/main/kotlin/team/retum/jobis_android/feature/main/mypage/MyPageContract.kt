package team.retum.jobis_android.feature.main.mypage

import androidx.annotation.StringRes
import team.retum.domain.entity.company.ReviewableCompanyEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.enums.Department
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class MyPageState(
    val studentInformation: StudentInformationEntity = StudentInformationEntity(
        studentName = "",
        studentGcn = "",
        department = Department.DEFAULT,
        profileImageUrl = "",
    ),
    val reviewableCompanies: List<ReviewableCompanyEntity> = emptyList(),
) : State

internal sealed interface MyPageSideEffect : SideEffect {
    object SuccessSignOut : MyPageSideEffect
    object SuccessEditProfileImage : MyPageSideEffect
    class Exception(@StringRes val message: Int) : MyPageSideEffect
    object InvalidFileExtension : MyPageSideEffect
}
