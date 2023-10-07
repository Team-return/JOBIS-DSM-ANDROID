package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.retum.data.remote.response.company.FetchCompaniesResponse
import team.retum.data.remote.response.company.FetchCompanyCountResponse
import team.retum.data.remote.response.company.FetchCompanyDetailResponse
import team.retum.data.remote.response.company.FetchReviewableCompaniesResponse
import team.retum.data.remote.url.JobisUrl

interface CompanyApi {
    @GET(JobisUrl.Company.student)
    suspend fun fetchCompanies(
        @Query("page") page: Int,
        @Query("name") name: String?,
    ): FetchCompaniesResponse

    @GET(JobisUrl.Company.details)
    suspend fun fetchCompanyDetails(
        @Path("company-id") companyId: Long,
    ): FetchCompanyDetailResponse

    @GET(JobisUrl.Company.reviewableCompanies)
    suspend fun fetchReviewableCompanies(): FetchReviewableCompaniesResponse

    @GET(JobisUrl.Company.companyCount)
    suspend fun fetchCompanyCount(
        @Query("page") page: Int,
        @Query("name") name: String?,
    ): FetchCompanyCountResponse
}
