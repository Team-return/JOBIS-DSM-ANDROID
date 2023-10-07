package team.retum.data.remote.datasource.code

import team.retum.data.remote.response.code.FetchCodesResponse
import team.retum.domain.enums.Type

interface CodeDataSource {
    suspend fun fetchCodes(
        keyword: String?,
        type: Type,
        parentCode: Long?,
    ): FetchCodesResponse
}
