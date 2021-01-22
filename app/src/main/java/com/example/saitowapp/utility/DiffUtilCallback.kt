package com.example.saitowapp.utility

import androidx.recyclerview.widget.DiffUtil
import com.example.saitowapp.model.DataOrders

class DiffUtilCallback : DiffUtil.ItemCallback<DataOrders>() {
    override fun areItemsTheSame(oldItem: DataOrders, newItem: DataOrders): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataOrders, newItem: DataOrders): Boolean {
        return oldItem.equals(newItem)
    }
}