package team.retum.jobis_android.root.navigation

object NavigationRoute {
    const val Splash = "Splash"
    const val SignIn = "SignIn"
    const val SignUp = "SignUp"
    const val Main = "Main"
    const val StudentInfo = "StudentInfo"
    const val VerifyEmail = "EmailVerify"
    const val SetPassword = "SetPassword"
    const val Recruitments = "Recruitments"
    const val RecruitmentDetails = "RecruitmentDetails/"
    const val Companies = "Companies"
    const val CompanyDetails = "CompanyDetails/"
    const val ResetPasswordVerifyEmail = "ResetPasswordVerifyEmail"
    const val ResetPassword = "ResetPassword"
    const val ComparePassword = "ComparePassword"

    const val RecruitmentId = "{${NavigationProperties.RECRUITMENT_ID}}"
    const val CompanyId = "{${NavigationProperties.COMPANY_ID}}"
    const val HasRecruitment = "{${NavigationProperties.HAS_RECRUITMENT}}"
    const val ReviewId = "{${NavigationProperties.REVIEW_ID}}"

    object Navigation {
        const val Home = "Home"
        const val BookmarkRecruitments = "BookmarkRecruitments"
        const val MyPage = "MyPage"
        const val Menu = "Menu"
    }

    object MainNavigation {
        const val BugReport = "BugReport"
        const val ReviewDetails = "ReviewDetails/"
        const val PostReview = "PostReview"
    }
}