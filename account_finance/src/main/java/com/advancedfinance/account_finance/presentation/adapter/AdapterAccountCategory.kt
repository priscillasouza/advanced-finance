package com.advancedfinance.account_finance.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ThemedSpinnerAdapter
import com.advancedfinance.account_finance.presentation.model.AccountCategoryModel

class AdapterAccountCategory(
    context: Context,
    private val listCategories: List<AccountCategoryModel>
): ArrayAdapter<AccountCategoryModel>(
    context,
    android.R.layout.simple_spinner_dropdown_item,
    listCategories
), ThemedSpinnerAdapter {

    override fun getItem(position: Int): AccountCategoryModel? {
        return listCategories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listCategories.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)
        val view = super.getView(position, convertView, parent)
        val textViewNameCategories = view.findViewById<TextView>(android.R.id.text1)
        textViewNameCategories.setText(listCategories[position].name)
        return view
    }
}