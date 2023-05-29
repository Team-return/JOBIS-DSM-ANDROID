package team.retum.domain.param.recruitment

data class FetchRecruitmentListParam(
    val page: Int,
    val code: Long?,
    val company: String?,
)