package team.retum.jobis_android.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UnderLineTextFieldWrapper(
    content: @Composable () -> Unit,
){
    Box(
        modifier = Modifier.height(90.dp),
    ){
        content()
    }
}