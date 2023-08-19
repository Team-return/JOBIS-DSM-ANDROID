package team.retum.domain.repository

import team.retum.domain.param.bugreport.BugReportParam

interface BugReportRepository {
    suspend fun bugReport(bugReportParam: BugReportParam)
}