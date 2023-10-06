package team.retum.jobis_android.navigation

object NavigationProperties {
    const val COMPANY_NAME = "companyName"
    const val RECRUITMENT_ID = "recruitment-id"
    const val COMPANY_ID = "company-id"
    const val REVIEW_ID = "review-id"
    const val WRITER = "writer"
}

internal fun String.toNavigationRoute() = "{${this}}"