package com.advancedfinance.account_finance.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.advancedfinance.account_finance.databinding.AccountFinanceAccountListItemBinding
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.extensions.toMoney

class AccountListAdapter(val onClickItem: (AccountModel) -> Unit) :
    RecyclerView.Adapter<AccountListAdapter.AccountListViewHolder>() {

    private var accountList = arrayListOf<AccountModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountListViewHolder {
        val binding =
            AccountFinanceAccountListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return AccountListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountListViewHolder, position: Int) {
        holder.onBind(accountList[position])
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<AccountModel>) {
        if (list.isNotEmpty()) {
            accountList.clear()
            accountList.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class AccountListViewHolder(
        private val layout: AccountFinanceAccountListItemBinding,
    ) :
        RecyclerView.ViewHolder(layout.root) {
        fun onBind(account: AccountModel) {
            layout.apply {
                textViewAccountName.text = account.name
                textViewAccountValue.text = account.startedBalance.toString().toMoney()
                cardViewAccountListItem.setOnClickListener {
                    onClickItem.invoke(account)
                }
            }
        }
    }
}