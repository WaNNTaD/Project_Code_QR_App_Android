package com.example.projectcodeqr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.projectcodeqr.R
import com.example.projectcodeqr.models.HighTechItem




class HighTechItemAdapter(
    private val context: Context,
    private val highTechItemList: List<HighTechItem>,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) : BaseAdapter() {

    override fun getCount(): Int {
        return highTechItemList.size
    }

    override fun getItem(position: Int): HighTechItem {
        return highTechItemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.adapter_item,null)

        return view
    }
}

