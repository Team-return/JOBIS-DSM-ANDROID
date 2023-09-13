package team.retum.domain.repository

import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.param.students.ResetPasswordParam

interface StudentRepository {
    suspend fun fetchStudentInformation(): StudentInformationEntity

    suspend fun comparePassword(password: String)

    suspend fun resetPassword(resetPasswordParam: ResetPasswordParam)

    suspend fun editProfileImage(editProfileImageParam: EditProfileImageParam)
}