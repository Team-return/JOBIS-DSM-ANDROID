package team.retum.data.remote.url

object JobisUrl {

    const val users = "/users"
    const val code = "/code"
    const val student = "/students"
    const val auth = "/auth"
    const val recruitment = "/recruitments"
    const val bookmarks = "/bookmarks"
    const val applications = "/applications"
    const val companies = "/companies"

    object User {
        const val login = "$users/login"
        const val reissue = "$users/reissue"
    }

    object Student {
        const val signup = "$student/signup"
        const val recruit = "$student/recruit"
        const val exists = "$student/exists"
        const val my = "$student/my"
    }

    object Code {
        const val tech = "$code/tech"
        const val job = "$code/job"
    }

    object Auth {
        const val code = "$auth/code"
    }

    object Recruitment {
        const val student = "$recruitment/student"
        const val bookmark = "$bookmarks/{recruitment-id}"
    }

    object Applications{
        const val employment = "$applications/employment/count"
        const val students = "$applications/students"
    }

    object Company{
        const val student = "$companies/student"
    }
}