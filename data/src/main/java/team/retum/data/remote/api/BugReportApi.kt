package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import team.retum.data.remote.request.bugs.BugReportRequest
import team.retum.data.remote.url.JobisUrl

interface BugReportApi {
    @POST(JobisUrl.Bugs.bugReport)
    suspend fun bugReport(
        @Body bugReportRequest: BugReportRequest,
    )
}