package team.retum.domain.repository

import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.param.user.SignUpParam

interface StudentRepository {
    suspend fun fetchStudentInformation(): StudentInformationEntity

    suspend fun comparePassword(password: String)

    suspend fun resetPassword(resetPasswordParam: ResetPasswordParam)

    suspend fun editProfileImage(editProfileImageParam: EditProfileImageParam)

    suspend fun checkStudentExists(checkStudentExistsParam: CheckStudentExistsParam)

    suspend fun signUp(signUpParam: SignUpParam)
}
