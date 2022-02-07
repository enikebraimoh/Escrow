package com.adashi.escrow.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.adashi.escrow.databinding.TransactionItemLayoutBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.order.allorders.Order
import com.adashi.escrow.models.shipmentpatch.Transaction
import ng.adashi.core.BaseAdapter

class TransactionsAdapter(val click : (vendor: Order)->Unit) : BaseAdapter<Order>(DiffCallBack()) {
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

    class DiffCallBack() : DiffUtil.ItemCallback<Order>() {
        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean = oldItem == newItem

        override fun areItemsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean = oldItem._id == newItem._id
    }

}