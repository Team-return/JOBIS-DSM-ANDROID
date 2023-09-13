package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.students.EditProfileImageRequest
import team.retum.data.remote.request.students.ResetPasswordRequest
import team.retum.data.remote.response.students.FetchStudentInformationResponse

interface StudentsDataSource {
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse

    suspend fun comparePassword(password: String)

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest)

    suspend fun editProfileImage(editProfileImageRequest: EditProfileImageRequest)

    suspend fun checkStudentExists(
        gcn: Int,
        name: String,
    )
}