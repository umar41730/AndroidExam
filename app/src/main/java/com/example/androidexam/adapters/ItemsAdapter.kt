package com.example.androidexam.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexam.model.ItemsModel
import com.example.androidexam.R

class ItemsAdapter(private var items: List<ItemsModel>) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("cjsfd","items   $items")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

        fun updateData(newData: List<ItemsModel>) {
            items = newData
            notifyDataSetChanged()
        }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemsModel) {
            itemView.findViewById<TextView>(R.id.itemName).text = item.itemName
            // You can bind other views here if needed
        }
    }
}