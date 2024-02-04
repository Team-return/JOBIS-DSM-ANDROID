package team.retum.data.remote.datasource.company

import team.retum.data.remote.response.company.FetchCompaniesResponse
import team.retum.data.remote.response.company.FetchCompanyCountResponse
import team.retum.data.remote.response.company.FetchCompanyDetailResponse
import team.retum.data.remote.response.company.FetchReviewableCompaniesResponse

interface CompanyDataSource {
    suspend fun fetchCompanies(
        page: Long,
        name: String?,
    ): FetchCompaniesResponse

    suspend fun fetchCompanyDetails(
        companyId: Long,
    ): FetchCompanyDetailResponse

    suspend fun fetchReviewableCompanies(): FetchReviewableCompaniesResponse

    suspend fun fetchCompanyCount(
        page: Long,
        name: String?,
    ): FetchCompanyCountResponse
}
