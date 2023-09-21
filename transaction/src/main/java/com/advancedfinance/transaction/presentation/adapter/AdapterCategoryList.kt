package com.advancedfinance.transaction.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ThemedSpinnerAdapter
import com.advancedfinance.category.presentation.model.CategoryModel

class AdapterCategoryList(
    context: Context,
    private val categoryList: List<CategoryModel> = arrayListOf()
): ArrayAdapter<CategoryModel>(
    context,
    android.R.layout.simple_spinner_dropdown_item,
    categoryList
), ThemedSpinnerAdapter {

    override fun getItem(position: Int): CategoryModel {
        return categoryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)
        val view = super.getView(position, convertView, parent)
        val textViewNameCategory = view.findViewById<TextView>(android.R.id.text1)
        textViewNameCategory.text = categoryList[position].name
        return view
    }
}