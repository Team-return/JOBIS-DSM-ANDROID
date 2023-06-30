package team.retum.domain.repository

import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.param.students.ResetPasswordParam

interface StudentsRepository {
    suspend fun fetchStudentInformation(): StudentInformationEntity

    suspend fun comparePassword(
        password: String,
    )

    suspend fun resetPassword(
        resetPasswordParam: ResetPasswordParam,
    )
}