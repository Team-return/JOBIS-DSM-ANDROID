package team.retum.domain.param.recruitment

data class FetchRecruitmentsParam(
    val page: Long,
    val name: String?,
    val jobCode: Long?,
    val techCode: String?,
    val winterIntern: Boolean,
)
