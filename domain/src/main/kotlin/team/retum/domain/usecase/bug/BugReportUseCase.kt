package team.retum.domain.usecase.bug

import team.retum.domain.param.bugreport.BugReportParam
import team.retum.domain.repository.BugRepository
import javax.inject.Inject

class BugReportUseCase @Inject constructor(
    private val bugRepository: BugRepository,
) {
    suspend operator fun invoke(bugReportParam: BugReportParam) = runCatching {
        bugRepository.reportBug(bugReportParam)
    }
}
