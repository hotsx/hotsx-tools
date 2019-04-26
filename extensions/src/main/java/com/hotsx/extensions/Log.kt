package com.hotsx.extensions

import android.util.Log

fun Any.logger(tip: String, msg: String) {
    Log.v(this.javaClass.name, "$tip:$msg")
}