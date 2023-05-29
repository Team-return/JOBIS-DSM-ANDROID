package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.user.Sex
import team.retum.domain.param.user.SignUpParam

data class SignUpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Sex,
    @SerializedName("class_room") val classRoom: Int,
    @SerializedName("number") val number: Int,
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
