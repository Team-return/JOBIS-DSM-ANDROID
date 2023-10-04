package team.retum.design_system.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.click(
    onClick: () -> Unit,
    disable: Boolean,
    interactionSource: MutableInteractionSource,
) = clickable(
    onClick = onClick,
    interactionSource = interactionSource,
    indication = null,
    enabled = !disable,
)
