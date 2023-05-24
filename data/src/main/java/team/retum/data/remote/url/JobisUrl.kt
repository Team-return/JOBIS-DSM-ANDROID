package team.retum.data.remote.url

object JobisUrl {

    const val users = "/users"
    const val code = "/code"
    const val student = "/students"
    const val auth = "/auth"
    const val recruitment = "/recruitments"

    object User {
        const val login = "$users/login"
        const val reissue = "$users/reissue"
    }

    object Student {
        const val signup = "$student/signup"
        const val recruit = "$student/recruit"
        const val exists = "$student/exists"
        const val main = "$student/main"
    }

    object Code {
        const val tech = "$code/tech"
        const val job = "$code/job"
    }

    object Auth {
        const val code = "$auth/code"
    }

    object Recruitment{
        const val student = "$recruitment/student"
    }
}