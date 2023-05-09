package team.retum.data.remote.request

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.AuthCodeType
import team.retum.domain.param.Sex
import team.retum.domain.param.SignUpParam

data class SignUpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("password") val password: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Sex,
    @SerializedName("class_room") val classRoom: Int,
    @SerializedName("number") val number: Int,
)

fun SignUpParam.toRequest() = SignUpRequest(
    email = this.email,
    phoneNumber = this.phoneNumber,
    password = this.password,
    grade = this.grade,
    name = this.name,
    gender = this.gender,
    classRoom = this.classRoom,
    number = this.number,
)
