package com.advancedfinance.category.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.advancedfinance.category.databinding.CategoryListItemBinding
import com.advancedfinance.category.presentation.model.CategoryModel

class CategoryListAdapter(val onClickItem: (CategoryModel) -> Unit) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    private var categoryList = arrayListOf<CategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<CategoryModel>) {
        if (list.isNotEmpty()) {
            categoryList.clear()
            categoryList.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class CategoryListViewHolder(
        private val layout: CategoryListItemBinding,
    ) :
        RecyclerView.ViewHolder(layout.root) {
        fun onBind(category: CategoryModel) {
            layout.apply {
                textViewCategoryName.text = category.name
                cardViewCategoryListItem.setOnClickListener {
                    onClickItem.invoke(category)
                }
            }
        }
    }
}