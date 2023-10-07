package team.retum.domain.repository

import team.retum.domain.entity.company.CompaniesEntity
import team.retum.domain.entity.company.CompanyCountEntity
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.ReviewableCompaniesEntity
import team.retum.domain.param.company.FetchCompaniesParam

interface CompanyRepository {
    suspend fun fetchCompanies(
        fetchCompaniesParam: FetchCompaniesParam,
    ): CompaniesEntity

    suspend fun fetchCompanyDetails(
        companyId: Long,
    ): CompanyDetailsEntity

    suspend fun fetchReviewableCompanies(): ReviewableCompaniesEntity

    suspend fun fetchCompanyCount(
        fetchCompaniesParam: FetchCompaniesParam,
    ): CompanyCountEntity
}
