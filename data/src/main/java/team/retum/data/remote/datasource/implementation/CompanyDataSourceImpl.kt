package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.CompanyApi
import team.retum.data.remote.datasource.declaration.CompanyDataSource
import team.retum.data.remote.response.FetchCompaniesResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class CompanyDataSourceImpl @Inject constructor(
    private val companyApi: CompanyApi,
) : CompanyDataSource {
    override suspend fun fetchCompanies(
        page: Int,
        name: String?,
    ): FetchCompaniesResponse = HttpHandler<FetchCompaniesResponse>().httpRequest {
        companyApi.fetchCompanies(
            page = page,
            name = name,
        )
    }.sendRequest()
}