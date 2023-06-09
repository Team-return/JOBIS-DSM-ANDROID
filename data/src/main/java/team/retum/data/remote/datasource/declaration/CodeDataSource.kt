package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.code.FetchCodesResponse
import team.retum.domain.param.code.Type

interface CodeDataSource {
    suspend fun fetchCodes(
        keyword: String?,
        type: Type,
        parentCode: Long?,
    ): FetchCodesResponse
}