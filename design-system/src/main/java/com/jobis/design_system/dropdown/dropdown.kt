package com.jobis.design_system.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography
import com.jobis.design_system.util.SmallHeightSpacer
import com.jobis.design_system.util.SmallShape
import com.jobis.design_system.util.click

@Composable
fun DropDown(
    title: String,
    disable: Boolean = false,
    list: List<String>,
) {

    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val rotateState by animateFloatAsState(
        targetValue = if(isExpanded) 180f else 0f
    )

    val outLineColor = if (disable) color.Gray400
    else color.Gray600

    val backgroundColor = if (disable) color.Gray400
    else color.Gray100

    val textColor = if (disable) color.Gray500
    else color.Gray900

    val drawable = if (disable) R.drawable.ic_direction_disabled
    else R.drawable.ic_direction_enabled

    Column {
        Row(
            modifier = Modifier
                .size(
                    width = 168.dp,
                    height = 48.dp,
                )
                .clickable { isExpanded = !isExpanded }
                .border(
                    width = 1.dp,
                    color = outLineColor,
                    shape = SmallShape,
                )
                .background(
                    color = backgroundColor
                )
                .padding(
                    start = 10.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 2.dp)
                    .padding(start = 6.dp),
                style = typography.body1,
                color = textColor,
            )
            Image(
                painter = painterResource(id = drawable),
                modifier = Modifier
                    .padding(top = 2.dp)
                    .click(
                        onClick = { if (!disable) isExpanded = !isExpanded },
                        disable = false,
                        interactionSource = interactionSource,
                    )
                    .rotate(rotateState),
                contentDescription = null,
            )
        }
        Spacer(modifier = SmallHeightSpacer)
        DropDownItemList(
            list = list,
            isExpanded = isExpanded,
        )
    }
}

@Composable
fun DropDownItemList(
    list: List<String>,
    isExpanded: Boolean,
) {

    Column(
        modifier = Modifier
            .width(
                width = 168.dp,
            )
            .border(
                width = 1.dp,
                color = color.Gray600,
                shape = SmallShape,
            )
    ) {
        AnimatedVisibility(isExpanded) {
            LazyColumn {
                items(items = list) {
                    DropDownItem(text = it)
                }
            }
        }
    }
}

@Composable
fun DropDownItem(
    text: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {

            }
            .padding(
                start = 10.dp,
                top = 16.dp
            )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                start = 6.dp,
                bottom = 14.dp
            ),
            style = typography.body3,
            color = color.Gray600,
        )
        Column(
            modifier = Modifier
                .size(
                    width = 148.dp,
                    height = 1.dp,
                )
                .background(
                    color = color.Gray500,
                )
        ) {}
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun DropDownPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        DropDown(
            title = "옵션",
            list = listOf("sleifjs", "selfijsef"),
        )
    }
}