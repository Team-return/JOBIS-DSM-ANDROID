package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.company.CompanyApi
import team.retum.data.remote.datasource.declaration.CompanyDataSource
import team.retum.data.remote.response.company.FetchCompaniesResponse
import team.retum.data.remote.response.company.FetchCompanyDetailResponse
import team.retum.data.remote.response.company.FetchReviewableCompaniesResponse
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

    override suspend fun fetchCompanyDetails(
        companyId: Long,
    ): FetchCompanyDetailResponse = HttpHandler<FetchCompanyDetailResponse>().httpRequest {
        companyApi.fetchCompanyDetails(
            companyId = companyId,
        )
    }.sendRequest()

    override suspend fun fetchReviewableCompanies() = HttpHandler<FetchReviewableCompaniesResponse>().httpRequest {
        companyApi.fetchReviewableCompanies()
    }.sendRequest()
}