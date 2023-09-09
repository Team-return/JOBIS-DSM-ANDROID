package team.retum.jobis_android.util.compose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import team.retum.jobis_android.root.navigation.navigateBottomMenu
import team.retum.jobis_android.util.compose.vibrate
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body4

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomBar(
    navController: NavController,
) {

    val context = LocalContext.current

    val tabs = listOf(
        NavigationItem.Home,
        NavigationItem.Bookmarked,
        NavigationItem.Profile,
        NavigationItem.Menu,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = Modifier
            .height(80.dp)
            .navigationBarsPadding()
            .graphicsLayer(
                clip = true,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
                shadowElevation = 10f,
            ),
        backgroundColor = JobisColor.Gray100,
    ) {

        val currentDestination = navBackStackEntry?.destination?.route

        tabs.forEach { tab ->
            BottomNavigationItem(
                selected = currentDestination == tab.route,
                onClick = {
                    navController.navigateBottomMenu(
                        navigationRoute = tab.route,
                        navGraphId = navController.graph.id,
                    )
                    vibrate(context = context)
                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(bottom = 8.dp),
                        painter = painterResource(id = tab.drawableId),
                        contentDescription = stringResource(id = tab.stringResId),
                    )
                },
                modifier = Modifier.padding(top = 4.dp),
                selectedContentColor = JobisColor.LightBlue,
                unselectedContentColor = JobisColor.Gray500,
                label = {
                    Body4(
                        text = stringResource(id = tab.stringResId),
                        color = JobisColor.LightBlue,
                    )
                },
                alwaysShowLabel = false,
            )
        }
    }
}
