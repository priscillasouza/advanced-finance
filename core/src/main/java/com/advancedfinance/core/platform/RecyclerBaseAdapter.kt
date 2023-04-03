package com.advancedfinance.core.platform

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.advancedfinance.core.Inflate
import com.advancedfinance.core.platform.RecyclerBaseAdapter.BaseViewHolder

abstract class RecyclerBaseAdapter<DATA : BaseModel, VIEW_HOLDER : BaseViewHolder<DATA>> :
    PagingDataAdapter<DATA, VIEW_HOLDER>(DiffUtils()) {


    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int, inflate: Inflate<ViewBinding>): VIEW_HOLDER
    override fun onBindViewHolder(viewHolder: VIEW_HOLDER, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class DiffUtils<DATA : BaseModel> : DiffUtil.ItemCallback<DATA>() {
        override fun areItemsTheSame(oldItem: DATA, newItem: DATA): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DATA, newItem: DATA): Boolean {
            return oldItem.equals(newItem)
        }

    }

    abstract class BaseViewHolder<T : BaseModel>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(model: T?)
    }
}