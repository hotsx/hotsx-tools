package com.hotsx.extensions

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 *  提示相关拓展
 */
fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes resId: Int) {
    Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
}

fun View.snackbar(msg: String): Snackbar = Snackbar
    .make(this, msg, Snackbar.LENGTH_SHORT)
    .apply { show() }

fun View.snackbar(@StringRes resId: Int) = Snackbar
    .make(this, resId, Snackbar.LENGTH_SHORT)
    .apply { show() }