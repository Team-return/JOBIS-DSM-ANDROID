package team.retum.data.remote.url

object JobisUrl {

    const val users = "/users"
    const val code = "/codes"
    const val student = "/students"
    const val auth = "/auth"
    const val recruitment = "/recruitments"
    const val bookmarks = "/bookmarks"
    const val applications = "/applications"
    const val companies = "/companies"
    const val review = "/reviews"
    const val files = "/files"

    object User {
        const val login = "$users/login"
        const val reissue = "$users/reissue"
    }

    object Student {
        const val signup = student
        const val recruit = "$student/recruit"
        const val exists = "$student/exists"
        const val my = "$student/my"
        const val password = "$student/password"
    }

    object Code {
        const val code = JobisUrl.code
    }

    object Auth {
        const val code = "$auth/code"
    }

    object Recruitment {
        const val student = "$recruitment/student"
        const val bookmark = "$bookmarks/${Properties.recruitmentId}"
        const val details = "$recruitment/${Properties.recruitmentId}"
    }

    object Applications{
        const val employment = "$applications/employment/count"
        const val students = "$applications/students"
        const val apply = "$applications/{recruitment-id}"
    }

    object Company{
        const val student = "$companies/student"
        const val details = "$companies/${Properties.companyId}"
    }

    object Review{
        const val reviews = "$review/${Properties.companyId}"
    }

    object Properties{
        const val recruitmentId = "{recruitment-id}"
        const val companyId = "{company-id}"
    }
}