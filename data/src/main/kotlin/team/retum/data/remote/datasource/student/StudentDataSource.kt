package team.retum.data.remote.datasource.student

import team.retum.data.remote.request.student.EditProfileImageRequest
import team.retum.data.remote.request.student.ResetPasswordRequest
import team.retum.data.remote.request.user.SignUpRequest
import team.retum.data.remote.response.student.FetchStudentInformationResponse
import team.retum.data.remote.response.user.SignUpResponse

interface StudentDataSource {
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse

    suspend fun comparePassword(password: String)

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest)

    suspend fun editProfileImage(editProfileImageRequest: EditProfileImageRequest)

    suspend fun checkStudentExists(
        gcn: Int,
        name: String,
    )

    suspend fun signUp(
        signUpRequest: SignUpRequest,
    ): SignUpResponse
}
