package team.retum.data.repository

import team.retum.data.remote.datasource.student.StudentsDataSource
import team.retum.data.remote.datasource.user.UserDataSource
import team.retum.data.remote.request.student.toRequest
import team.retum.data.remote.request.user.toRequest
import team.retum.data.remote.response.student.toEntity
import team.retum.data.remote.response.user.SignInResponse
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.param.user.SignUpParam
import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

// TODO setUserInfo LocalDataSource로 이동
class StudentRepositoryImpl @Inject constructor(
    private val studentsDataSource: StudentsDataSource,
    private val userDataSource: UserDataSource,
) : StudentRepository {
    override suspend fun fetchStudentInformation(): StudentInformationEntity =
        studentsDataSource.fetchStudentInformation().toEntity()

    override suspend fun comparePassword(password: String) {
        studentsDataSource.comparePassword(password = password)
    }

    override suspend fun resetPassword(resetPasswordParam: ResetPasswordParam) {
        studentsDataSource.resetPassword(resetPasswordRequest = resetPasswordParam.toRequest())
    }

    override suspend fun editProfileImage(editProfileImageParam: EditProfileImageParam) {
        studentsDataSource.editProfileImage(editProfileImageRequest = editProfileImageParam.toRequest())
    }

    override suspend fun checkStudentExists(
        checkStudentExistsParam: CheckStudentExistsParam,
    ) {
        studentsDataSource.checkStudentExists(
            gcn = checkStudentExistsParam.gcn,
            name = checkStudentExistsParam.name,
        )
    }

    override suspend fun signUp(
        signUpParam: SignUpParam,
    ) {

        val response = studentsDataSource.signUp(
            signUpRequest = signUpParam.toRequest(),
        )

        userDataSource.run {
            setUserInfo(
                signInResponse = SignInResponse(
                    accessToken = response.accessToken,
                    accessExpiresAt = response.accessExpiresAt,
                    refreshToken = response.refreshToken,
                    refreshExpiresAt = response.refreshExpiresAt,
                    authority = response.authority,
                )
            )
        }
    }
}