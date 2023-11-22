package team.retum.jobis_android.navigation

object NavigationRoute {
    const val Splash = "splash"
    const val Root = "root"

    const val Auth = "auth"
    const val Main = "main"
    const val User = "user"

    object BottomNavigation {
        const val Home = "home"
        const val BookmarkRecruitments = "bookmarkRecruitments"
        const val MyPage = "myPage"
        const val Menu = "menu"
    }
}

object AuthDestinations {
    const val SignIn = "signIn"
    const val SignUp = "signUp"
    const val ResetPasswordVerifyEmail = "resetPasswordVerifyEmail"
    const val ResetPassword = "resetPassword"
    const val ComparePassword = "comparePassword"
    const val StudentInfo = "studentInfo"
    const val VerifyEmail = "verifyEmail"
    const val SetPassword = "setPassword"
}

object MainDestinations {
    const val Recruitments = "recruitments/"
    const val RecruitmentDetails = "recruitmentDetails/"
    const val Companies = "companies"
    const val CompanyDetails = "companyDetails/"
    const val ReviewDetails = "reviewDetails/"
    const val PostReview = "postReview"
}

object UserDestination {
    const val ReportBug = "reportBug"
    const val Notifications = "notifications"
}
