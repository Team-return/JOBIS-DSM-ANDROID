package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.bug.BugApi
import team.retum.data.remote.datasource.declaration.BugDataSource
import team.retum.data.remote.request.bugs.ReportBugRequest
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