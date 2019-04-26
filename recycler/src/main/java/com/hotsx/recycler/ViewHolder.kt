package com.hotsx.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(val convertView: View) : RecyclerView.ViewHolder(convertView) {

    fun <T> bindData(position: Int, item: T) {}
}