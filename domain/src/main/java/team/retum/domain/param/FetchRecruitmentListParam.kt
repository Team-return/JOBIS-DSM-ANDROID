package team.retum.domain.param

data class FetchRecruitmentListParam(
    val page: Int,
    val code: Long?,
    val company: String?,
)