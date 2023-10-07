package team.retum.data.repository

import team.retum.data.remote.datasource.code.CodeDataSource
import team.retum.data.remote.response.code.toEntity
import team.retum.domain.entity.code.CodesEntity
import team.retum.domain.param.code.FetchCodesParam
import team.retum.domain.repository.CodeRepository
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val codeDataSource: CodeDataSource,
) : CodeRepository {
    override suspend fun fetchCodes(
        fetchCodesParam: FetchCodesParam,
    ): CodesEntity = codeDataSource.fetchCodes(
        keyword = fetchCodesParam.keyword,
        type = fetchCodesParam.type,
        parentCode = fetchCodesParam.parentCode,
    ).toEntity()
}
