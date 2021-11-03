package com.adashi.escrow.ui.addbank

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.adashi.escrow.models.listofbanks.ListOfBanks
import com.adashi.escrow.models.listofbanks.ListOfBanksItem

import android.widget.TextView
import com.adashi.escrow.R

class BanksAdapter(context: Context, var array: MutableList<ListOfBanksItem>) :
    ArrayAdapter<ListOfBanksItem>(
        context,
        R.layout.banks_item,
        array
    ) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = array.get(position)
        var listItem = convertView

        if (listItem == null) listItem =
            LayoutInflater.from(context).inflate(R.layout.banks_item, parent, false)

        if (item != null){
            var bankName: TextView = listItem?.findViewById(R.id.my_bank_name)!!
            bankName.setText(item.name)
        }

        return listItem!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = array.get(position)
        var listItem = convertView

        if (listItem == null) listItem =
            LayoutInflater.from(context).inflate(R.layout.banks_item, parent, false)

     if (item != null){
         var bankName: TextView = listItem?.findViewById(R.id.my_bank_name)!!
         bankName.setText(item.name)
     }

        return listItem!!

    }


}