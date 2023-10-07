package team.retum.domain.entity.bookmark

data class BookmarkedRecruitmentsEntity(
    val bookmarks: List<BookmarkedRecruitmentEntity>,
)

data class BookmarkedRecruitmentEntity(
    val companyName: String,
    val recruitmentId: Long,
    val createdAt: String,
)
