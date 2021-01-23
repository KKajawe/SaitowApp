package com.example.saitowapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saitowapp.R
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.model.Orders
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_listitem.view.*

class OrderFilterListAdapter(private val ordersData: Orders) :
    RecyclerView.Adapter<OrderFilterListAdapter.DataViewHolder>() {

    class DataViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(orderItem: DataOrders) {
            itemView.apply {
                tv_orderId.text = orderItem.id.toString()
                tv_gross_price.text = orderItem.operator.prices.get(0).gross.toString()
                tv_net_price.text = orderItem.operator.prices.get(0).net.toString()
                tv_payment_method.text = orderItem.payment.name
                tv_date.text = orderItem.createdAt
            }
        }


    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(ordersData.data.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_listitem, parent, false),
        )

    override fun getItemCount(): Int = ordersData.data.size


    fun updateUI(newOrders: Orders) {
        this.ordersData.data.apply {
            clear()
            addAll(newOrders.data)
        }

    }
}