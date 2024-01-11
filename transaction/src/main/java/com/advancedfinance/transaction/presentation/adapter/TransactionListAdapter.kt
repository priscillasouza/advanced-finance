package com.advancedfinance.transaction.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.advancedfinance.core.extensions.toMoney
import com.advancedfinance.transaction.databinding.TransactionListItemBinding
import com.advancedfinance.transaction.presentation.model.TransactionModel
import com.advancedfinance.transaction.presentation.screen.transaction_list.TransactionListFragmentDirections

class TransactionListAdapter(val onClickItem: (TransactionModel) -> Unit) :
    RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>() {

    private var transactionList = arrayListOf<TransactionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val binding =
            TransactionListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return TransactionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        holder.onBind(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TransactionModel>) {
        if (list.isNotEmpty()) {
            transactionList.clear()
            transactionList.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class TransactionListViewHolder(
        private val layout: TransactionListItemBinding
    ) :
        RecyclerView.ViewHolder(layout.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(transaction: TransactionModel) {
            layout.apply {
                textViewTansactionValue.text = transaction.value.toString().toMoney()
                textViewTansactionDescription.text = transaction.description
                textViewTransactionDate.text = transaction.date
                textViewTansactionCategory.text = transaction.category?.name
                textViewTansactionAccount.text = transaction.account?.name
                cardViewTransactionListItem.setOnClickListener {
                    onClickItem.invoke(transaction)
                    /*val action =
                       TransactionListFragmentDirections.transactionActionTransactionTransactionlistfragmentToTransactionTransactionfragment()
                    cardViewTransactionListItem.findNavController().navigate(action)*/
                }
            }
        }
    }
}