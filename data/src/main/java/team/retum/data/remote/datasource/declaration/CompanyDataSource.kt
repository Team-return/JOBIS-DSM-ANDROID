package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.FetchCompaniesResponse

interface CompanyDataSource {
    suspend fun fetchCompanies(
        page: Int,
        name: String?,
    ): FetchCompaniesResponse
}