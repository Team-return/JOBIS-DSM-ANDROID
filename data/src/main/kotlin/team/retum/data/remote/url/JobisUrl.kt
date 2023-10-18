package team.retum.data.remote.url

object JobisUrl {

    private const val users = "/users"
    private const val code = "/codes"
    private const val auth = "/auth"
    private const val recruitment = "/recruitments"
    private const val applications = "/applications"
    private const val companies = "/companies"
    private const val review = "/reviews"
    private const val bugs = "/bugs"

    internal const val bookmarks = "/bookmarks"
    internal const val files = "/files"
    internal const val student = "/students"

    const val imageUrl = "https://jobis-store.s3.ap-northeast-2.amazonaws.com/"

    object Bookmark {
        const val bookmark = "$bookmarks/${Properties.recruitmentId}"
    }

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
        const val forgottenPassword = "$student/forgotten_password"
        const val profile = "$student/profile"
    }

    object Code {
        const val code = JobisUrl.code
    }

    object Auth {
        const val code = "$auth/code"
        const val reissue = "$auth/reissue"
    }

    object Recruitment {
        const val student = "$recruitment/student"
        const val details = "$recruitment/${Properties.recruitmentId}"
        const val recruitmentCount = "$recruitment/student/count"
    }

    object Application {
        const val employment = "$applications/employment/count"
        const val students = "$applications/students"
        const val apply = "$applications/{recruitment-id}"
    }

    object Company {
        const val student = "$companies/student"
        const val details = "$companies/${Properties.companyId}"
        const val reviewableCompanies = "$companies/review"
        const val companyCount = "$companies/student/count"
    }

    object Review {
        const val reviews = "$review/${Properties.companyId}"
        const val reviewDetails = "$review/details/${Properties.reviewId}"
        const val postReview = review
    }

    object Bugs {
        const val reportBug = bugs
    }

    object Properties {
        const val recruitmentId = "{recruitment-id}"
        const val companyId = "{company-id}"
        const val reviewId = "{review-id}"
    }
}
