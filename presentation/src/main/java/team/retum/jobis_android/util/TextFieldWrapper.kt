package team.retum.jobis_android.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UnderLineTextFieldWrapper(
    content: @Composable () -> Unit,
){
    Box(
        modifier = Modifier.fillMaxHeight(0.15f),
    ){
        content()
    }
}