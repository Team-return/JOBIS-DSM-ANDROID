package team.retum.jobis_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.retum.jobis_android.feature.bugreport.BugReportScreen
import team.retum.jobis_android.feature.notification.NotificationsScreen
import team.retum.jobis_android.util.compose.navigation.baseComposable

internal fun NavGraphBuilder.userNavigation(
    navigatePopBackStack: () -> Unit,
) {
    navigation(
        route = NavigationRoute.User,
        startDestination = UserDestination.ReportBug,
    ) {
        baseComposable(route = UserDestination.ReportBug) {
            BugReportScreen(navigatePopBackStack = navigatePopBackStack)
        }

        baseComposable(route = UserDestination.Notifications) {
            NotificationsScreen()
        }
    }
}
