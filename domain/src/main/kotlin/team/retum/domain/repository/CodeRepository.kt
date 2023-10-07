package team.retum.domain.repository

import team.retum.domain.entity.code.CodesEntity
import team.retum.domain.param.code.FetchCodesParam

interface CodeRepository {
    suspend fun fetchCodes(
        fetchCodesParam: FetchCodesParam,
    ): CodesEntity
}
