package team.retum.jobis_android.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle

@Composable
fun CollectWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    collect: suspend () -> Unit,
){
    LaunchedEffect(key1 = Unit){
        lifecycle.repeatOnLifecycle(
            state = Lifecycle.State.STARTED,
        ){
            collect()
        }
    }
}