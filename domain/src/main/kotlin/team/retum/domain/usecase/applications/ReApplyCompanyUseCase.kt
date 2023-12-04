package team.retum.domain.usecase.applications

import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.repository.ApplicationsRepository
import javax.inject.Inject

class ReApplyCompanyUseCase @Inject constructor(
    private val applicationsRepository: ApplicationsRepository,
) {
    suspend operator fun invoke(
        applicationId: Long,
        applyCompanyParam: ApplyCompanyParam,
    ) = runCatching {
        applicationsRepository.reApplyCompany(
            applicationId = applicationId,
            applyCompanyParam = applyCompanyParam,
        )
    }
}