package team.retum.data.remote.api

import retrofit2.http.GET
import team.retum.data.remote.response.FetchStudentInformationResponse
import team.retum.data.remote.url.JobisUrl

interface StudentsApi {
    @GET(JobisUrl.Student.my)
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse
}