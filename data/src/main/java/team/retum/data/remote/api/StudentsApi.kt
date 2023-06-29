package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import team.retum.data.remote.response.students.FetchStudentInformationResponse
import team.retum.data.remote.url.JobisUrl

interface StudentsApi {
    @GET(JobisUrl.Student.my)
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse

    @GET(JobisUrl.Student.password)
    suspend fun comparePassword(
        @Query("password") password: String,
    )
}