package team.retum.jobis_android.util.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.retum.jobisui.util.jobisClickable

@Composable
fun ScreenLayout(
    onClick: () -> Unit,
    button: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable(
                rippleEnabled = false,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onClick()
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        content()
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 30.dp,
            ),
            verticalArrangement = Arrangement.Bottom,
        ) {
            button()
        }
    }
}