package team.retum.jobis_android.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

const val Duration = 1500
@Composable
fun ColumnScope.Animated(
    visible: Boolean,
    isBounce: Boolean = false,
    content: @Composable () -> Unit,
){

    val enter = if(isBounce){
        fadeIn(
            animationSpec = tween(Duration),
        ).plus(
            expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        )
    }else {
        fadeIn() + expandVertically()
    }

    val exit = if(isBounce){
        fadeOut(
            animationSpec = tween(Duration)
        ).plus(
            shrinkVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        )
    }else{
        fadeOut() + shrinkVertically()
    }

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit,
    ) {
        content()
    }
}

@Composable
fun RowScope.Animated(
    visible: Boolean,
    isBounce: Boolean = false,
    content: @Composable () -> Unit,
){

    val enter = if(isBounce){
        fadeIn(
            animationSpec = tween(Duration),
        ).plus(
            expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        )
    }else {
        fadeIn() + expandHorizontally()
    }

    val exit = if(isBounce){
        fadeOut(
            animationSpec = tween(Duration)
        ).plus(
            shrinkVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        )
    }else {
        fadeOut() + shrinkHorizontally()
    }

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit,
    ) {
        content()
    }
}

@Composable
fun BoxScope.Animated(
    visible: Boolean,
    isBounce: Boolean = false,
    content: @Composable () -> Unit,
){

    val enter = if(isBounce){
        fadeIn(
            animationSpec = tween(Duration),
        ).plus(
            expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        )
    }else {
        fadeIn() + expandIn()
    }

    AnimatedVisibility(
        visible = visible,
        enter = enter,
    ) {
        content()
    }
}