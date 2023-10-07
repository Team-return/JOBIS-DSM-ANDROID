package team.retum.data.remote.datasource.student

import team.retum.data.remote.api.StudentApi
import team.retum.data.remote.request.student.EditProfileImageRequest
import team.retum.data.remote.request.student.ResetPasswordRequest
import team.retum.data.remote.request.user.SignUpRequest
import team.retum.data.remote.response.student.FetchStudentInformationResponse
import team.retum.data.remote.response.user.SignUpResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class StudentDataSourceImpl @Inject constructor(
    private val studentApi: StudentApi,
) : StudentDataSource {
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

    override suspend fun checkStudentExists(
        gcn: Int,
        name: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            studentApi.checkStudentExists(
                gcn = gcn,
                name = name,
            )
        }.sendRequest()

    override suspend fun signUp(
        signUpRequest: SignUpRequest,
    ) = HttpHandler<SignUpResponse>()
        .httpRequest {
            studentApi.signUp(
                signUpRequest = signUpRequest,
            )
        }.sendRequest()
}
