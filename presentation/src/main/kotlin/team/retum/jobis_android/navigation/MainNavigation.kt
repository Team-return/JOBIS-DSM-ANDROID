package team.retum.jobis_android.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navigation
import team.retum.jobis_android.feature.company.CompaniesScreen
import team.retum.jobis_android.feature.company.CompanyDetailsScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentDetailsScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentsScreen
import team.retum.jobis_android.feature.review.PostReviewScreen
import team.retum.jobis_android.feature.review.ReviewDetailsScreen
import team.retum.jobis_android.util.compose.animation.slideInLeft
import team.retum.jobis_android.util.compose.animation.slideInRight
import team.retum.jobis_android.util.compose.animation.slideOutLeft
import team.retum.jobis_android.util.compose.animation.slideOutRight
import team.retum.jobis_android.util.compose.navigation.baseComposable

internal fun NavGraphBuilder.mainNavigation(
    putString: (key: String, value: String) -> Unit,
    getString: (key: String) -> String?,
    navigateToRecruitmentDetails: (recruitmentId: Long) -> Unit,
    getPreviousDestination: () -> String?,
    navigateToCompanyDetails: (companyId: Long) -> Unit,
    navigateToReviewDetails: (reviewId: String) -> Unit,
    popBackStack: () -> Unit,
) {
    navigation(
        route = NavigationRoute.Main,
        startDestination = MainDestinations.Recruitments,
    ) {
        baseComposable(
            route = MainDestinations.Recruitments,
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
            popExitTransition = fadeOut(tween(300)),
        ) {
            RecruitmentsScreen(
                putString = putString,
                navigateToRecruitmentDetails = navigateToRecruitmentDetails,
            )
        }
        // TODO getPreviousDestination
        baseComposable(
            route = MainDestinations.RecruitmentDetails.plus(NavigationProperties.RECRUITMENT_ID.toNavigationRoute()),
            arguments = listOf(
                getArgument(
                    NavigationProperties.RECRUITMENT_ID,
                    NavType.LongType,
                ),
            ),
            enterTransition = slideInLeft(),
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
            popExitTransition = slideOutRight(),
        ) {
            RecruitmentDetailsScreen(
                recruitmentId = it.arguments?.getLong(NavigationProperties.RECRUITMENT_ID),
                getPreviousDestination = getPreviousDestination,
                navigateToCompanyDetails = navigateToCompanyDetails,
            )
        }

        baseComposable(
            route = MainDestinations.Companies,
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
            popExitTransition = fadeOut(tween(300)),
        ) {
            CompaniesScreen(navigateToCompanyDetails = navigateToCompanyDetails)
        }

        baseComposable(
            route = MainDestinations.CompanyDetails.plus(NavigationProperties.COMPANY_ID.toNavigationRoute()),
            arguments = listOf(
                getArgument(NavigationProperties.COMPANY_ID, NavType.LongType),
            ),
            enterTransition = slideInLeft(),
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
            popExitTransition = slideOutRight(),
        ) {
            val companyId = it.arguments?.getLong(NavigationProperties.COMPANY_ID)
            CompanyDetailsScreen(
                companyId = companyId ?: 0,
                getPreviousDestination = getPreviousDestination,
                navigateToRecruitmentDetails = navigateToRecruitmentDetails,
                navigateToReviewDetails = navigateToReviewDetails,
                putString = putString,
            )
        }

        baseComposable(
            route = MainDestinations.ReviewDetails.plus(NavigationProperties.REVIEW_ID.toNavigationRoute()),
            arguments = listOf(
                getArgument(NavigationProperties.REVIEW_ID, NavType.StringType),
            ),
            enterTransition = slideInLeft(),
            exitTransition = slideOutRight(),
            popExitTransition = slideOutRight(),
        ) {
            ReviewDetailsScreen(
                reviewId = it.arguments?.getString(NavigationProperties.REVIEW_ID) ?: "",
                getString = getString,
            )
        }

        baseComposable(
            route = MainDestinations.PostReview.plus(NavigationProperties.COMPANY_ID.toNavigationRoute()),
            arguments = listOf(
                getArgument(NavigationProperties.COMPANY_ID, NavType.LongType),
            ),
        ) {
            val companyId = it.arguments?.getLong(NavigationProperties.COMPANY_ID)
            PostReviewScreen(
                companyId = companyId ?: 0,
                navigatePopBackStack = popBackStack,
            )
        }
    }
}
