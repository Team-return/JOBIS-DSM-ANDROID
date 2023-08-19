package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.bugs.BugReportRequest

interface BugReportDataSource {
    suspend fun bugReport(bugReportRequest: BugReportRequest)
}