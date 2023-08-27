package team.retum.domain.usecase.company

import team.retum.domain.repository.CompanyRepository
import javax.inject.Inject

class FetchCompanyDetailsUseCase @Inject constructor(
    private val companyRepository: CompanyRepository,
) {
    suspend operator fun invoke(
        companyId: Long,
    ) = runCatching {
        companyRepository.fetchCompanyDetails(
            companyId = companyId,
        )
    }
}