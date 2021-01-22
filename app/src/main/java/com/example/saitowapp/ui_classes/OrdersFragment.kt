package com.example.saitowapp.ui_classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.saitowapp.R
import com.example.saitowapp.adapter.OrderListAdapter
import com.example.saitowapp.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.frag_orderlist.*

class OrdersFragment : Fragment() {
    private lateinit var adapterOrder: OrderListAdapter
    private val viewModel by activityViewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.frag_orderlist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterOrder = OrderListAdapter()
        observeLiveData()
        orders_list_view.adapter = adapterOrder


    }

    private fun observeLiveData() {
        viewModel.getOrderList().observe(this, {
            adapterOrder.submitList(it)
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            viewModel.saveData(-1, "", false)
            val fragmanger = activity?.supportFragmentManager
            val fragTransaction = fragmanger?.beginTransaction()
            fragTransaction?.replace(R.id.container, LoginFragment())
            fragTransaction?.commit()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}