package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.BugReportDataSource
import team.retum.data.remote.request.bugs.toRequest
import team.retum.domain.param.bugreport.BugReportParam
import team.retum.domain.repository.BugReportRepository
import javax.inject.Inject

class BugReportRepositoryImpl @Inject constructor(
    private val bugReportDataSource: BugReportDataSource,
) : BugReportRepository {
    override suspend fun bugReport(bugReportParam: BugReportParam) {
        bugReportDataSource.bugReport(bugReportParam.toRequest())
    }
}