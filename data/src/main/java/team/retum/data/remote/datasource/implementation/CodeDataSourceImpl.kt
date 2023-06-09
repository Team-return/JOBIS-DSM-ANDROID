package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.CodeApi
import team.retum.data.remote.datasource.declaration.CodeDataSource
import team.retum.data.remote.response.code.FetchCodesResponse
import team.retum.data.util.HttpHandler
import team.retum.domain.param.code.Type
import javax.inject.Inject

class CodeDataSourceImpl @Inject constructor(
    private val codeApi: CodeApi,
): CodeDataSource {
    override suspend fun fetchCodes(
        keyword: String?,
        type: Type,
        parentCode: Long?,
    ) = HttpHandler<FetchCodesResponse>().httpRequest {
        codeApi.fetchCodes(
            keyword = keyword,
            type = type,
            parentCode = parentCode,
        )
    }.sendRequest()
}