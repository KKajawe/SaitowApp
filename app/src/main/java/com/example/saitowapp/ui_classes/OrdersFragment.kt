package com.example.saitowapp.ui_classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.saitowapp.R
import com.example.saitowapp.adapter.OrderListAdapter
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.frag_orderlist.*

class OrdersFragment : Fragment() {
    var adapterOrder: OrderListAdapter = OrderListAdapter()
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


        observeLiveData()


    }

    private fun observeLiveData() {

        viewModel.getOrderList().observe(viewLifecycleOwner, orderListObserver)
        orders_list_view.adapter = adapterOrder
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.saveData(-1, "", false)
                val fragmanger = activity?.supportFragmentManager
                val fragTransaction = fragmanger?.beginTransaction()
                fragTransaction?.replace(R.id.container, LoginFragment())
                fragTransaction?.commit()
                return true
            }
            R.id.menu_sort -> {
                when (viewModel.sortParam) {
                    "+created_at" -> {
                        Toast.makeText(context, "sort descending", Toast.LENGTH_LONG).show()
                        viewModel.sortParam = "-created_at"
                        item.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.sort_descending)
                    }
                    "-created_at" -> {
                        Toast.makeText(context, "sort Ascending", Toast.LENGTH_LONG).show()
                        viewModel.sortParam = "+created_at"
                        item.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.sort_ascending)
                    }
                    else -> {
                        Toast.makeText(context, "sort Ascending", Toast.LENGTH_LONG).show()
                        viewModel.sortParam = "+created_at"
                        item.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.sort_ascending)
                    }
                }
                viewModel.getOrderList().removeObserver(orderListObserver)
                observeLiveData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    var orderListObserver: Observer<PagedList<DataOrders>> = Observer<PagedList<DataOrders>> {
        adapterOrder.submitList(it)
        adapterOrder.notifyDataSetChanged()
    }
}