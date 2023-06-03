package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import team.retum.data.remote.response.code.FetchCodesResponse
import team.retum.data.remote.url.JobisUrl

interface CodeApi {
    @GET(JobisUrl.Code.code)
    suspend fun fetchCodes(
        @Query("keyword") keyword: String?,
        @Query("type") type: Type,
        @Query("parent_code") parentCode: Long?,
    ): FetchCodesResponse
}

enum class Type{
    JOB, TECH, BUSINESS_AREA
}