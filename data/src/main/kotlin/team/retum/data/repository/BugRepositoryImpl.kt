package team.retum.data.repository

import team.retum.data.remote.datasource.bug.BugDataSource
import team.retum.data.remote.request.bug.toRequest
import team.retum.domain.param.bugreport.BugReportParam
import team.retum.domain.repository.BugRepository
import javax.inject.Inject

class BugRepositoryImpl @Inject constructor(
    private val bugDataSource: BugDataSource,
) : BugRepository {
    override suspend fun reportBug(bugReportParam: BugReportParam) {
        bugDataSource.reportBug(bugReportParam.toRequest())
    }
}
