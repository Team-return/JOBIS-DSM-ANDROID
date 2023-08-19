package team.retum.domain.usecase.bug

import team.retum.domain.param.bugreport.BugReportParam
import team.retum.domain.repository.BugReportRepository
import javax.inject.Inject

class ReportBugUseCase @Inject constructor(
    private val bugReportRepository: BugReportRepository,
){
    suspend operator fun invoke(bugReportParam: BugReportParam) = runCatching {
        bugReportRepository.bugReport(bugReportParam)
    }
}