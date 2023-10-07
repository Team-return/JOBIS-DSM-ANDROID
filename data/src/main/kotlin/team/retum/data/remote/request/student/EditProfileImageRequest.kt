package team.retum.data.remote.request.student

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.students.EditProfileImageParam

data class EditProfileImageRequest(
    @SerializedName("profile_image_url") val profileImageUrl: String,
)

internal fun EditProfileImageParam.toRequest() = EditProfileImageRequest(
    profileImageUrl = this.profileImageUrl,
)
