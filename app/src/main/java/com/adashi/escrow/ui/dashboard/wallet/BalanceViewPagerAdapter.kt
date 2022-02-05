package com.adashi.escrow.ui.dashboard.wallet

import android.graphics.Color
import android.graphics.ColorMatrix
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import com.adashi.escrow.R

class BalanceViewPagerAdapter(private var balance : MutableList<String>,private var title : MutableList<String>)
    : RecyclerView.Adapter<BalanceViewPagerAdapter.BalanceViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        return BalanceViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.wallet_balance_item,parent,false))
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.balance.text = balance[position]
        holder.balanceTitle.text = title[position]

        when(title[position]){
            "Pending Balance" -> {
                holder.background.setCardBackgroundColor(Color.parseColor("#CF7C00"))
                holder.icon.setImageResource(R.drawable.ic_pending_icon)
                holder.balanceTitle.setTextColor(Color.WHITE)
            }
            "Dispute Balance" ->{
                holder.background.setCardBackgroundColor(Color.parseColor("#7B2424"))
                holder.icon.setImageResource(R.drawable.ic_dispute_icon)
                holder.balanceTitle.setTextColor(Color.WHITE)
            }
            else ->{
                holder.background.setCardBackgroundColor(Color.BLUE)
                holder.icon.setImageResource(R.drawable.ic_sucess_icon)
                holder.balanceTitle.setTextColor(Color.parseColor("#FFCE00"))
            }
        }

    }

    inner class BalanceViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val balance : TextView = itemView.findViewById(R.id.the_balance)
        val balanceTitle : TextView = itemView.findViewById(R.id.bl_title)
        val background : CardView = itemView.findViewById(R.id.card_background)
        val icon : ImageView = itemView.findViewById(R.id.ic_sucess_icon)
    }

}