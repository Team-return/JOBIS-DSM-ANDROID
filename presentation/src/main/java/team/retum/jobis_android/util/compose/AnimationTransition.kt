package team.retum.jobis_android.util.compose

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

internal fun slideOutLeft(
    initialOffSetX: Int = -300,
    duration: Int = 300,
    easing: Easing = FastOutSlowInEasing,
) = slideOutHorizontally(
    animationSpec = tween(
        durationMillis = duration,
        easing = easing,
    ),
    targetOffsetX = { initialOffSetX }
) + fadeOut(
    animationSpec = tween(
        durationMillis = duration,
    )
)

internal fun slideOutRight(
    initialOffSetX: Int = 300,
    duration: Int = 300,
    easing: Easing = FastOutSlowInEasing,
) = slideOutHorizontally(
    animationSpec = tween(
        durationMillis = duration,
        easing = easing,
    ),
    targetOffsetX = { initialOffSetX }
) + fadeOut(
    animationSpec = tween(
        durationMillis = duration,
    )
)

internal fun slideInRight(
    targetOffSetX: Int = -300,
    duration: Int = 300,
    easing: Easing = FastOutSlowInEasing,
) = slideInHorizontally(
    animationSpec = tween(
        durationMillis = duration,
        easing = easing,
    ),
    initialOffsetX = { targetOffSetX }
) + fadeIn(
    animationSpec = tween(
        durationMillis = duration,
    )
)

internal fun slideInLeft(
    targetOffSetX: Int = 300,
    duration: Int = 300,
    easing: Easing = FastOutSlowInEasing,
) = slideInHorizontally(
    animationSpec = tween(
        durationMillis = duration,
        easing = easing,
    ),
    initialOffsetX = { targetOffSetX }
) + fadeIn(
    animationSpec = tween(
        durationMillis = duration,
    )
)