package team.retum.data.remote.url

object JobisUrl {

    const val users = "/users"
    const val code = "/code"
    const val student = "/students"
    const val auth = "/auth"

    object User {
        const val login = "$users/login"
        const val reissue = "$users/reissue"
    }

    object Student {
        const val signup = "$student/signup"
        const val recruit = "$student/recruit"
    }

    object Code {
        const val tech = "$code/tech"
        const val job = "$code/job"
    }

    object Auth {
        const val code = "$auth/code"
    }
}