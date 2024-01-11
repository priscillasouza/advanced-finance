package com.advancedfinance.transaction.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ThemedSpinnerAdapter
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel

class AdapterPeriodTypeList(
    context: Context,
    private val periodTypeList: List<PeriodTypeModel> = arrayListOf()
): ArrayAdapter<PeriodTypeModel>(
    context,
    android.R.layout.simple_spinner_dropdown_item,
    periodTypeList
), ThemedSpinnerAdapter {

    override fun getItem(position: Int): PeriodTypeModel {
        return periodTypeList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return periodTypeList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)
        val view = super.getView(position, convertView, parent)
        val textViewNamePeriodType = view.findViewById<TextView>(android.R.id.text1)
        textViewNamePeriodType.text = periodTypeList[position].name
        return view
    }
}