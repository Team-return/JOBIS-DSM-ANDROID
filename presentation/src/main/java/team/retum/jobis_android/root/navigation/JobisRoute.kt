package team.retum.jobis_android.root.navigation

object JobisRoute {
    const val Splash = "Splash"
    const val SignIn = "SignIn"
    const val SignUp = "SignUp"
    const val Main = "Main"
    const val StudentInfo = "StudentInfo"
    const val VerifyEmail = "EmailVerify"
    const val SetPassword = "SetPassword"
    const val Recruitments = "Recruitments"
    const val RecruitmentDetails = "RecruitmentDetails/{recruitment-id}"
    const val Companies = "Companies"
    const val CompanyDetails = "CompanyDetails/{company-id}/{has-recruitment}"
    const val ChangePassword = "ChangePassword"

    object Navigation {
        const val Home = "Home"
        const val BookmarkedRecruitments = "Bookmarked"
        const val MyPage = "MyPage"
        const val Menu = "Menu"
    }
}