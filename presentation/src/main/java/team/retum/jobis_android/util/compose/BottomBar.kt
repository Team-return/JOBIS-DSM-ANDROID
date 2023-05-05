package team.retum.jobis_android.util.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import team.retum.jobisui.colors.JobisColor

@Composable
fun BottomBar() {
    BottomNavigation(
        modifier = Modifier
            .graphicsLayer(
                clip = true,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
                shadowElevation = 10f,
            ),
        backgroundColor = JobisColor.Gray100,
    ) {}
}