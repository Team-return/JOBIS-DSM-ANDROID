package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.enums.Gender
import team.retum.domain.param.user.SignUpParam

data class SignUpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("grade") val grade: Long,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Gender,
    @SerializedName("class_room") val classRoom: Long,
    @SerializedName("number") val number: Long,
)

fun SignUpParam.toRequest() = SignUpRequest(
    email = this.email,
    password = this.password,
    grade = this.grade,
    name = this.name,
    gender = this.gender,
    classRoom = this.classRoom,
    number = this.number,
)
