package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import team.retum.data.remote.request.bug.ReportBugRequest
import team.retum.data.remote.url.JobisUrl

interface BugApi {
    @POST(JobisUrl.Bugs.reportBug)
    suspend fun reportBug(
        @Body reportBugRequest: ReportBugRequest,
    )
}