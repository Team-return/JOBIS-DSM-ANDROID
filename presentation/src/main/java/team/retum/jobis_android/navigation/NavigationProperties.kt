package team.retum.jobis_android.navigation

object NavigationProperties {
    const val COMPANY_NAME = "companyName"
    const val RECRUITMENT_ID = "recruitment-id"
    const val COMPANY_ID = "company-id"
    const val HAS_RECRUITMENT = "has-recruitment"
    const val REVIEW_ID = "review-id"
}

internal fun String.toNavigationRoute() = "{${this}}"