package com.jobis.jobis_android.util

import android.app.Activity
import android.content.Context
import android.content.Intent

fun <T>startActivity(
    context: Context,
    to: Class<T>,
){
    context.startActivity(
        Intent(context, to)
    )
}