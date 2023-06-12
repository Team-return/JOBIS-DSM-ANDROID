package team.retum.data.remote.response.bookmark

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentsEntity

data class FetchBookmarkedRecruitmentsResponse(
    @SerializedName("bookmarks") val bookmarks: List<BookmarkedRecruitment>,
)

data class BookmarkedRecruitment(
    @SerializedName("company_name") val companyName: String,
    @SerializedName("recruitment_id") val recruitmentId: Long,
    @SerializedName("created_at") val createdAt: String,
)

fun FetchBookmarkedRecruitmentsResponse.toEntity() = BookmarkedRecruitmentsEntity(
    bookmarks = this.bookmarks.map { it.toEntity() },
)

private fun BookmarkedRecruitment.toEntity() = BookmarkedRecruitmentEntity(
    companyName = this.companyName,
    recruitmentId = this.recruitmentId,
    createdAt = this.createdAt,
)
