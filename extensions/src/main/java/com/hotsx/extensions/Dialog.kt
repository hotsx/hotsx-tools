package com.hotsx.extensions

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

class DialogConfig {

    @StringRes
    var titleId: Int? = null
    var title: String? = null

    @LayoutRes
    var layoutId: Int? = null

    @StringRes
    var messageId: Int? = null
    var message: String? = null
}

fun Activity.alart() {

}