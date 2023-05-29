package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.company.FetchCompaniesResponse
import team.retum.data.remote.response.company.FetchCompanyDetailResponse

interface CompanyDataSource {
    suspend fun fetchCompanies(
        page: Int,
        name: String?,
    ): FetchCompaniesResponse

    suspend fun fetchCompanyDetails(
        companyId: Int,
    ): FetchCompanyDetailResponse
}