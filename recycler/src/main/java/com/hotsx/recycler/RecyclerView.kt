package com.hotsx.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerAdapter<T>(
    @LayoutRes val layoutId: Int,
    private var dataList: MutableList<T> = ArrayList()
) : RecyclerView.Adapter<ViewHolder>() {

    private val typeHeader = 0
    private val typeNormal = 1
    private val typeFooter = 2

    @LayoutRes
    private var headerLayoutId: Int? = null
    @LayoutRes
    private var footerLayoutId: Int? = null

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutId, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        convert(holder, dataList[position], position)
    }

    abstract fun convert(holder: ViewHolder, item: T, position: Int)

    fun getDataList() = dataList

    fun addItem(item: T) {
        dataList.add(item)
        notifyItemInserted(dataList.size - 1)
    }
}

class AdapterConfig<T> {

    var dataList: MutableList<T>? = null
    var mOnItemClick: ((View, T, Int) -> Unit)? = null
    var mOnItemLongClick: ((View, T, Int) -> Unit)? = null
    var convertView: ((holder: ViewHolder, item: T, position: Int) -> Unit)? = null
    var layoutManger: RecyclerView.LayoutManager? = null

    fun withItems(items: MutableList<T>) {
        dataList = items
    }

    fun onItemClick(onItemClick: (view: View, T, Int) -> Unit) {
        mOnItemClick = onItemClick
    }

    fun onItemLongClick(onItemLongClick: (view: View, T, Int) -> Unit) {
        mOnItemLongClick = onItemLongClick
    }

    fun onConvert(convert: (holder: ViewHolder, item: T, position: Int) -> Unit) {
        convertView = convert
    }
}

fun <T> RecyclerView.deployAdapter(@LayoutRes layoutId: Int, variableId: Int, config: AdapterConfig<T>.() -> Unit):
        RecyclerAdapter<T> {
    val adapterConfig = AdapterConfig<T>().apply(config)
    val recyclerAdapter = object : RecyclerAdapter<T>(layoutId, adapterConfig.dataList ?: ArrayList()) {
        override fun convert(holder: ViewHolder, item: T, position: Int) {
            holder.bindData(variableId, item)
            holder.convertView.setOnClickListener {
                adapterConfig.mOnItemClick?.invoke(it, item, position)
            }
            holder.convertView.setOnLongClickListener {
                adapterConfig.mOnItemLongClick?.invoke(it, item, position)
                return@setOnLongClickListener true
            }
            adapterConfig.convertView?.invoke(holder, item, position)
        }
    }
    layoutManager = adapterConfig.layoutManger ?: LinearLayoutManager(context)
    adapter = recyclerAdapter
    return recyclerAdapter
}