package com.jobis.jobis_android.util

import android.app.Activity
import android.content.Context
import android.content.Intent

fun startActivity(
    context: Context,
    to: Activity,
){
    context.startActivity(
        Intent(context, to::class.java)
    )
}