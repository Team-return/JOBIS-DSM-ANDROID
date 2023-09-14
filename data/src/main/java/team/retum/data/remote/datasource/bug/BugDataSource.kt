package team.retum.data.remote.datasource.bug

import team.retum.data.remote.request.bugs.ReportBugRequest

interface BugDataSource {
    suspend fun reportBug(reportBugRequest: ReportBugRequest)
}