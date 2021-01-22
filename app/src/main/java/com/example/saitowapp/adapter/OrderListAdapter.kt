package com.example.saitowapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.saitowapp.R
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.utility.DiffUtilCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_listitem.view.*

class OrderListAdapter() : PagedListAdapter<DataOrders, OrderListAdapter.RecyclrViewHolder>(
    DiffUtilCallback()
) {

    class RecyclrViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclrViewHolder =
        RecyclrViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_listitem, parent, false),
        )

    override fun onBindViewHolder(holder: RecyclrViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}
