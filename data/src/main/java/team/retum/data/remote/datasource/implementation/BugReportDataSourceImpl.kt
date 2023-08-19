package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.BugReportApi
import team.retum.data.remote.datasource.declaration.BugReportDataSource
import team.retum.data.remote.request.bugs.BugReportRequest
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class BugReportDataSourceImpl @Inject constructor(
    private val bugReportApi: BugReportApi,
) : BugReportDataSource {
    override suspend fun bugReport(bugReportRequest: BugReportRequest) =
        HttpHandler<Unit>().httpRequest {
            bugReportApi.bugReport(bugReportRequest)
        }.sendRequest()
}