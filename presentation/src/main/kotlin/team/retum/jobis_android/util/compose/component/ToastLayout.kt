package team.retum.jobis_android.util.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ToastLayout(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.padding(
            top = 48.dp,
            start = 22.dp,
            end = 22.dp,
        ),
        verticalArrangement = Arrangement.Top,
    ) {
        content()
    }
}
