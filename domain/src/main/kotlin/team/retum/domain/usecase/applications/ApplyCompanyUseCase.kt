package team.retum.domain.usecase.applications

import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.repository.ApplicationsRepository
import javax.inject.Inject

class ApplyCompanyUseCase @Inject constructor(
    private val applicationsRepository: ApplicationsRepository,
) {
    suspend operator fun invoke(
        recruitmentId: Long,
        applyCompanyParam: ApplyCompanyParam,
    ) = runCatching {
        applicationsRepository.applyCompany(
            recruitmentId = recruitmentId,
            applyCompanyParam = applyCompanyParam,
        )
    }
}
