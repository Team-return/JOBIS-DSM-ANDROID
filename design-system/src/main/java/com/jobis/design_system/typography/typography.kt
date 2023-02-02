package com.jobis.design_system.typography

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.design_system.R

val fontFamily = FontFamily(
    Font(R.font.noto_sans_kr_black, FontWeight.Black),
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold),
    Font(R.font.noto_sans_kr_medium, FontWeight.Medium),
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal),
    Font(R.font.noto_sans_kr_light, FontWeight.Light),
    Font(R.font.noto_sans_kr_thin, FontWeight.Thin)
)

object typography {

    val platformTextStyle = PlatformTextStyle(
        includeFontPadding = false
    )

    val heading1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 60.sp,
        platformStyle = platformTextStyle,
    )

    val heading2 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 54.sp,
        platformStyle = platformTextStyle,
    )

    val heading3 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 48.sp,
        platformStyle = platformTextStyle,
    )

    val heading4 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 40.sp,
        platformStyle = platformTextStyle,
    )

    val heading5 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        platformStyle = platformTextStyle,
    )

    val heading6 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        platformStyle = platformTextStyle,
    )

    val body1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        platformStyle = platformTextStyle,
    )

    val body2 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        platformStyle = platformTextStyle,
    )

    val body3 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        platformStyle = platformTextStyle,
    )

    val body4 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        platformStyle = platformTextStyle,
    )

    val caption = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        platformStyle = platformTextStyle,
    )
}

@Preview(
    showSystemUi = true,
)
@Composable
fun Test() {
    Row {
        Column {
            Heading1("Heading1")
            Heading2("Heading2")
            Heading3("Heading3")
            Heading4("Heading4")
            Heading5("Heading5")
            Heading6("Heading6")
        }
        Column {
            Body1("Body1")
            Body2("Body2")
            Body3("Body3")
            Body4("Body4")
            Caption("Caption")
        }
    }
}

@Composable
fun Heading1(
    text: String,
) {
    Text(
        text = text,
        style = typography.heading1,
    )
}

@Composable
fun Heading2(
    text: String,
) {
    Text(
        text = text,
        style = typography.heading2,
    )
}

@Composable
fun Heading3(
    text: String,
) {
    Text(
        text = text,
        style = typography.heading3,
    )
}

@Composable
fun Heading4(
    text: String,
) {
    Text(
        text = text,
        style = typography.heading4,
    )
}

@Composable
fun Heading5(
    text: String,
) {
    Text(
        text = text,
        style = typography.heading5,
    )
}

@Composable
fun Heading6(
    text: String,
) {
    Text(
        text = text,
        style = typography.heading6,
    )
}

@Composable
fun Body1(
    text: String,
) {
    Text(
        text = text,
        style = typography.body1,
    )
}

@Composable
fun Body2(
    text: String,
) {
    Text(
        text = text,
        style = typography.body2,
    )
}

@Composable
fun Body3(
    text: String,
) {
    Text(
        text = text,
        style = typography.body3,
    )
}

@Composable
fun Body4(
    text: String,
) {
    Text(
        text = text,
        style = typography.body4,
    )
}

@Composable
fun Caption(
    text: String,
) {
    Text(
        text = text,
        style = typography.caption,
    )
}
