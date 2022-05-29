package com.example.android.politicalpreparedness.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("app:setList")
fun <T> bindListWithRecyclerView(recyclerView: RecyclerView, data: T?) {
    data?.let { list ->
        if (recyclerView.adapter is GenericAdapter<*>) {
            (recyclerView.adapter as GenericAdapter<T>).setData(list)
        }
    }
}