package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.StudentApi
import team.retum.data.remote.datasource.declaration.StudentsDataSource
import team.retum.data.remote.request.students.EditProfileImageRequest
import team.retum.data.remote.request.students.ResetPasswordRequest
import team.retum.data.remote.response.students.FetchStudentInformationResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class StudentsDataSourceImpl @Inject constructor(
    private val studentApi: StudentApi,
) : StudentsDataSource {
    override suspend fun fetchStudentInformation(): FetchStudentInformationResponse =
        HttpHandler<FetchStudentInformationResponse>().httpRequest {
            studentApi.fetchStudentInformation()
        }.sendRequest()

    override suspend fun comparePassword(password: String) =
        HttpHandler<Unit>().httpRequest {
            studentApi.comparePassword(password = password)
        }.sendRequest()

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest) =
        HttpHandler<Unit>().httpRequest {
            studentApi.resetPassword(
                resetPasswordRequest = resetPasswordRequest,
            )
        }.sendRequest()

    override suspend fun editProfileImage(editProfileImageRequest: EditProfileImageRequest) =
        HttpHandler<Unit>().httpRequest {
            studentApi.editProfileImage(editProfileImageRequest = editProfileImageRequest)
        }.sendRequest()
}