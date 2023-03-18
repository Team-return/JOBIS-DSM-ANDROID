package team.retum.design_system.switchbutton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(
    showBackground = true,
)
fun SwitchPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        var check by remember { mutableStateOf(false) }
        SwitchButton(
            onCheck = { check = it },
            isCheck = check,
        )
    }
}

@Composable
fun SwitchButton(
    onCheck: (Boolean) -> Unit,
    isCheck: Boolean,
    disabled: Boolean = false,
) {

    val thumbColor = if (disabled) color.Gray500
    else color.Gray100

    val trackColor = if (disabled) color.Gray400
    else color.Gray500


    Switch(
        checked = isCheck,
        onCheckedChange = onCheck,
        colors = SwitchDefaults.colors(
            checkedThumbColor = color.Gray100,
            checkedTrackColor = color.CheckBlue,
            uncheckedThumbColor = thumbColor,
            uncheckedTrackColor = trackColor,
        ),
        enabled = !disabled,
    )
}