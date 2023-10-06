package team.retum.domain.repository

import team.retum.domain.param.bugreport.ReportBugParam

interface BugRepository {
    suspend fun reportBug(reportBugParam: ReportBugParam)
}