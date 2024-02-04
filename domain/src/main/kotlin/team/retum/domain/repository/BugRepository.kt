package team.retum.domain.repository

import team.retum.domain.param.bugreport.BugReportParam

interface BugRepository {
    suspend fun reportBug(bugReportParam: BugReportParam)
}
