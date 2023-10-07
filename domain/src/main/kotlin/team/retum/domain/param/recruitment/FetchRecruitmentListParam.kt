package team.retum.domain.param.recruitment

data class FetchRecruitmentListParam(
    val page: Int,
    val jobCode: Long?,
    val techCode: String?,
    val name: String?,
)
