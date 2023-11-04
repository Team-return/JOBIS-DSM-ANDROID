package team.retum.jobis_android.util.compose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jobis.jobis_android.R
import team.retum.jobis_android.navigation.NavigationRoute

sealed class NavigationItem(
    @DrawableRes val drawableId: Int,
    @StringRes val stringResId: Int,
    val route: String,
) {
    object Home : NavigationItem(
        drawableId = R.drawable.ic_home,
        stringResId = R.string.bottom_nav_home,
        route = NavigationRoute.BottomNavigation.Home,
    )

    object Bookmarked : NavigationItem(
        drawableId = R.drawable.ic_bookmark,
        stringResId = R.string.bottom_nav_bookmark,
        route = NavigationRoute.BottomNavigation.BookmarkRecruitments,
    )

    object Profile : NavigationItem(
        drawableId = R.drawable.ic_profile,
        stringResId = R.string.bottom_nav_my_page,
        route = NavigationRoute.BottomNavigation.MyPage,
    )

    object Menu : NavigationItem(
        drawableId = R.drawable.ic_menu,
        stringResId = R.string.bottom_nav_menu,
        route = NavigationRoute.BottomNavigation.Menu,
    )
}
