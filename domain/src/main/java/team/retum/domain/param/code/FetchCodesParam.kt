package team.retum.domain.param.code

data class FetchCodesParam(
    val keyword: String?,
    val type: Type,
    val parentCode: Long?,
)

enum class Type{
    JOB, TECH, BUSINESS_AREA
}