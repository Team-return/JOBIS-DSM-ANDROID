package team.retum.jobis_android.feature.main.home

import team.retum.domain.entity.applications.AppliedCompanyEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.enums.Department
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

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
