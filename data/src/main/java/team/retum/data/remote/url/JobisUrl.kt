package team.retum.data.remote.url

object JobisUrl {

    const val users = "/users"
    const val code = "/code"
    const val student = "/students"

    object USER{
        const val login = "$users/login"
        const val reissue = "$users/reissue"
    }

    object STUDENT{
        const val signup = "$student/signup"
        const val recruit = "$student/recruit"
    }

    object CODE{
        const val tech = "$code/tech"
        const val job = "$code/job"
    }
}