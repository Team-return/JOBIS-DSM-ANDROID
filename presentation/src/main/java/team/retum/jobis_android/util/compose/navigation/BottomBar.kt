package team.retum.jobis_android.util.compose.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body4

@Composable
fun BottomBar(
    navController: NavController,
) {

    var currentSelectedItem by remember { mutableStateOf(JobisRoute.Navigation.Home) }

    val tabs = listOf(
        NavigationItem.Home,
        NavigationItem.Recruitment,
        NavigationItem.Profile,
        NavigationItem.Menu,
    )

    BottomNavigation(
        modifier = Modifier
            .height(68.dp)
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
        tabs.forEach { tab ->
            BottomNavigationItem(
                selected = currentSelectedItem == tab.route,
                onClick = {
                    navController.navigate(tab.route)
                    currentSelectedItem = tab.route
                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(
                            bottom = 8.dp,
                        ),
                        painter = painterResource(id = tab.drawableId),
                        contentDescription = stringResource(id = tab.stringResId),
                    )
                },
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
