package team.retum.data.repository

import team.retum.data.remote.datasource.company.CompanyDataSource
import team.retum.data.remote.response.company.toEntity
import team.retum.domain.entity.company.CompaniesEntity
import team.retum.domain.entity.company.CompanyCountEntity
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.ReviewableCompaniesEntity
import team.retum.domain.param.company.FetchCompaniesParam
import team.retum.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyDataSource: CompanyDataSource,
) : CompanyRepository {
    override suspend fun fetchCompanies(
        fetchCompaniesParam: FetchCompaniesParam,
    ): CompaniesEntity = companyDataSource.fetchCompanies(
        page = fetchCompaniesParam.page,
        name = fetchCompaniesParam.name,
    ).toEntity()

    override suspend fun fetchCompanyDetails(
        companyId: Long,
    ): CompanyDetailsEntity = companyDataSource.fetchCompanyDetails(
        companyId = companyId,
    ).toEntity()

    override suspend fun fetchReviewableCompanies(): ReviewableCompaniesEntity =
        companyDataSource.fetchReviewableCompanies().toEntity()

    override suspend fun fetchCompanyCount(
        fetchCompaniesParam: FetchCompaniesParam,
    ): CompanyCountEntity = companyDataSource.fetchCompanyCount(
        page = fetchCompaniesParam.page,
        name = fetchCompaniesParam.name,
    ).toEntity()
}