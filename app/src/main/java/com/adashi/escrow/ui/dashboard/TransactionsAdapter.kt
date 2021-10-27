package com.adashi.escrow.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.adashi.escrow.databinding.TransactionItemLayoutBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.Transaction
import ng.adashi.core.BaseAdapter

class TransactionsAdapter(val click : (vendor: Transaction)->Unit) : BaseAdapter<Transaction>(DiffCallBack()) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val view = TransactionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return view
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        val item = getItem(position)
        (binding as TransactionItemLayoutBinding).data = item
        binding.root.setOnClickListener { click(item) }
        binding.executePendingBindings()
    }

    class DiffCallBack() : DiffUtil.ItemCallback<Transaction>() {
        override fun areContentsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean = oldItem == newItem

        override fun areItemsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean = oldItem._id == newItem._id
    }

}