package com.advancedfinance.account_finance.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ThemedSpinnerAdapter
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel

class AdapterAccountType(
    context: Context,
    private val listAccountTypes: List<AccountTypeModel> = arrayListOf()
): ArrayAdapter<AccountTypeModel>(
    context,
    android.R.layout.simple_spinner_dropdown_item,
    listAccountTypes
), ThemedSpinnerAdapter {

    override fun getItem(position: Int): AccountTypeModel {
        return listAccountTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listAccountTypes.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)
        val view = super.getView(position, convertView, parent)
        val textViewNameAccountType = view.findViewById<TextView>(android.R.id.text1)
        textViewNameAccountType.text = listAccountTypes[position].name
        return view
    }
}