package team.retum.jobis_android.util.compose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jobis.jobis_android.R
import team.retum.jobis_android.root.navigation.NavigationRoute

sealed class NavigationItem(
    @DrawableRes val drawableId: Int,
    @StringRes val stringResId: Int,
    val route: String,
) {
    object Home : NavigationItem(
        drawableId = R.drawable.ic_home,
        stringResId = R.string.bottom_nav_home,
        route = NavigationRoute.Navigation.Home,
    )

    object Bookmarked : NavigationItem(
        drawableId = R.drawable.ic_recruitment,
        stringResId = R.string.bottom_nav_recruitment,
        route = NavigationRoute.Navigation.BookmarkRecruitments,
    )

    object Profile : NavigationItem(
        drawableId = R.drawable.ic_profile,
        stringResId = R.string.bottom_nav_my_page,
        route = NavigationRoute.Navigation.MyPage,
    )

    object Menu : NavigationItem(
        drawableId = R.drawable.ic_menu,
        stringResId = R.string.bottom_nav_menu,
        route = NavigationRoute.Navigation.Menu,
    )
}