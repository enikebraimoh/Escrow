package com.adashi.escrow.ui.settings.bankaccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.adashi.escrow.databinding.BankAccountItemLayoutBinding
import com.adashi.escrow.databinding.FragmentWithdrawalBanksBinding
import com.adashi.escrow.databinding.TransactionItemLayoutBinding
import com.adashi.escrow.models.addbank.Account
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.shipmentpatch.Transaction
import ng.adashi.core.BaseAdapter

class GetAllBanksAdapter(val click : (vendor: Account)->Unit) : BaseAdapter<Account>(DiffCallBack()) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val view = BankAccountItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return view
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        val item = getItem(position)
        (binding as BankAccountItemLayoutBinding).data = item
        binding.root.setOnClickListener { click(item) }
        binding.executePendingBindings()
    }

    class DiffCallBack() : DiffUtil.ItemCallback<Account>() {
        override fun areContentsTheSame(
            oldItem: Account,
            newItem: Account
        ): Boolean = oldItem == newItem

        override fun areItemsTheSame(
            oldItem: Account,
            newItem: Account
        ): Boolean = oldItem._id == newItem._id
    }

}