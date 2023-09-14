package team.retum.data.remote.api.bug

import retrofit2.http.Body
import retrofit2.http.POST
import team.retum.data.remote.request.bugs.ReportBugRequest
import team.retum.data.remote.url.JobisUrl

interface BugApi {
    @POST(JobisUrl.Bugs.reportBug)
    suspend fun reportBug(
        @Body reportBugRequest: ReportBugRequest,
    )
}