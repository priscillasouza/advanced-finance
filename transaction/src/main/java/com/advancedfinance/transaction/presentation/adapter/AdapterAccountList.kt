package com.advancedfinance.transaction.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ThemedSpinnerAdapter
import com.advancedfinance.account_finance.presentation.model.AccountModel

class AdapterAccountList(
    context: Context,
    private val accountList: List<AccountModel> = arrayListOf()
): ArrayAdapter<AccountModel>(
    context,
    android.R.layout.simple_spinner_dropdown_item,
    accountList
), ThemedSpinnerAdapter {

    override fun getItem(position: Int): AccountModel {
        return accountList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return accountList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)
        val view = super.getView(position, convertView, parent)
        val textViewNameCategory = view.findViewById<TextView>(android.R.id.text1)
        textViewNameCategory.text = accountList[position].name
        return view
    }
}