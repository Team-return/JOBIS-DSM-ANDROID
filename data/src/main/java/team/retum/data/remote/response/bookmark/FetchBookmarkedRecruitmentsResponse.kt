package team.retum.data.remote.response.bookmark

import com.google.gson.annotations.SerializedName

data class FetchBookmarkedRecruitmentsResponse(
    @SerializedName("bookmarks") val bookmarks: List<BookmarkedRecruitment>,
)

data class BookmarkedRecruitment(
    @SerializedName("company_name") val companyName: String,
    @SerializedName("recruitment_id") val recruitmentId: Long,
    @SerializedName("created_at") val createdAt: String,
)
