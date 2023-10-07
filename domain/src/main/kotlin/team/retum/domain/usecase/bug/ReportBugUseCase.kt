package team.retum.domain.usecase.bug

import team.retum.domain.param.bugreport.ReportBugParam
import team.retum.domain.repository.BugRepository
import javax.inject.Inject

class ReportBugUseCase @Inject constructor(
    private val bugRepository: BugRepository,
) {
    suspend operator fun invoke(reportBugParam: ReportBugParam) = runCatching {
        bugRepository.reportBug(reportBugParam)
    }
}
