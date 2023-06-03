package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.api.Type
import team.retum.data.remote.response.code.FetchCodesResponse

interface CodeDataSource {
    suspend fun fetchCodes(
        keyword: String?,
        type: Type,
        parentCode: Long?,
    ): FetchCodesResponse
}