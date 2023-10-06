package team.retum.data.remote.datasource.bug

import team.retum.data.remote.api.BugApi
import team.retum.data.remote.request.bug.ReportBugRequest
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class BugDataSourceImpl @Inject constructor(
    private val bugApi: BugApi,
) : BugDataSource {
    override suspend fun reportBug(reportBugRequest: ReportBugRequest) =
        HttpHandler<Unit>().httpRequest {
            bugApi.reportBug(reportBugRequest)
        }.sendRequest()
}