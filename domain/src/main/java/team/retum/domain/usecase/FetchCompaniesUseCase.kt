package team.retum.domain.usecase

import team.retum.domain.param.FetchCompaniesParam
import team.retum.domain.repository.CompanyRepository
import javax.inject.Inject

class FetchCompaniesUseCase @Inject constructor(
    private val companyRepository: CompanyRepository,
) {
    suspend operator fun invoke(
        fetchCompaniesParam: FetchCompaniesParam,
    ) = runCatching{
        companyRepository.fetchCompanies(
            fetchCompaniesParam = fetchCompaniesParam,
        )
    }
}