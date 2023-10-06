package team.retum.domain.param.code

import team.retum.domain.enums.Type

data class FetchCodesParam(
    val keyword: String?,
    val type: Type,
    val parentCode: Long?,
)