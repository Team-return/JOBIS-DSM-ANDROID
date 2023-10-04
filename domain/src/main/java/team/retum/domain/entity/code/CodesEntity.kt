package team.retum.domain.entity.code

data class CodesEntity(
    val codes: List<CodeEntity>,
)

data class CodeEntity(
    val code: Long,
    val keyword: String,
)
