package team.retum.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import team.retum.data.remote.request.students.EditProfileImageRequest
import team.retum.data.remote.request.students.ResetPasswordRequest
import team.retum.data.remote.request.user.SignUpRequest
import team.retum.data.remote.response.students.FetchStudentInformationResponse
import team.retum.data.remote.response.user.SignUpResponse
import team.retum.data.remote.url.JobisUrl

interface StudentApi {
    @GET(JobisUrl.Student.my)
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse

    @GET(JobisUrl.Student.password)
    suspend fun comparePassword(
        @Query("password") password: String,
    )

    @PATCH(JobisUrl.Student.password)
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest,
    )

    @PATCH(JobisUrl.Student.profile)
    suspend fun editProfileImage(
        @Body editProfileImageRequest: EditProfileImageRequest,
    ): Response<Void>

    @GET(JobisUrl.Student.exists)
    suspend fun checkStudentExists(
        @Query("gcn") gcn: Int,
        @Query("name") name: String,
    )

    @POST(JobisUrl.student)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    ): SignUpResponse
}