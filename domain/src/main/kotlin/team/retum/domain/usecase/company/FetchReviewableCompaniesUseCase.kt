package team.retum.domain.usecase.company

import team.retum.domain.repository.CompanyRepository
import javax.inject.Inject

class FetchReviewableCompaniesUseCase @Inject constructor(
    private val companyRepository: CompanyRepository,
) {
    suspend operator fun invoke() = runCatching {
        companyRepository.fetchReviewableCompanies()
    }
}