package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.students.ResetPasswordRequest
import team.retum.data.remote.response.students.FetchStudentInformationResponse

interface StudentsDataSource {
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse

    suspend fun comparePassword(
        password: String,
    )

    suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest,
    )
}