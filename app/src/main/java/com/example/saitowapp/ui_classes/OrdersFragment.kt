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
import com.example.saitowapp.adapter.OrderFilterListAdapter
import com.example.saitowapp.adapter.OrderListAdapter
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.utility.Status
import com.example.saitowapp.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.frag_orderlist.*

class OrdersFragment : Fragment() {
    var adapterOrder: OrderListAdapter = OrderListAdapter()
    private lateinit var adapterOrdersFilter: OrderFilterListAdapter
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
        mprogress_bar.visibility=View.VISIBLE
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getOrderList().observe(viewLifecycleOwner, orderListObserver)
        orders_list_view.adapter = adapterOrder
        mprogress_bar.visibility=View.GONE

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.saveData(-1, "", false)
                MainActivity().onChangeNavigation(activity?.supportFragmentManager!!,LoginFragment())
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
                if (viewModel.isFilter) {
                    viewModel.getOrderList().removeObserver(orderListObserver)
                    setFilterObserver()
                } else {
                    viewModel.getOrderList().removeObserver(orderListObserver)
                    observeLiveData()
                }

            }
            R.id.menu_filter -> {
                if (viewModel.isFilter) {
                    viewModel.isFilter = false
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.filter_icon)
                    viewModel.getOrderList().removeObserver(orderListObserver)
                    observeLiveData()
                } else {
                    var customDialog = Custom_Dialog()
                    customDialog.showDialogWithAction(View.OnClickListener {
                        viewModel.getOrderList().removeObserver(orderListObserver)
                        setFilterObserver()
                        customDialog.dismiss()
                        viewModel.isFilter = true
                        item.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.filtersort)
                    }, activity?.supportFragmentManager!!)

                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    var orderListObserver: Observer<PagedList<DataOrders>> = Observer<PagedList<DataOrders>> {
        adapterOrder.submitList(it)
        adapterOrder.notifyDataSetChanged()
    }


    private fun setFilterObserver() {
        viewModel.getFilterList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mprogress_bar.visibility=View.GONE
                        adapterOrdersFilter = OrderFilterListAdapter(resource.data?.body()!!)
                        orders_list_view.adapter = adapterOrdersFilter
                    }
                    Status.ERROR -> {
                        mprogress_bar.visibility=View.GONE
                        Toast.makeText(context, "Unable to load filter Data!!", Toast.LENGTH_LONG)
                            .show()
                    }
                    Status.LOADING -> {
                        mprogress_bar.visibility=View.VISIBLE
                    }
                }
            }
        })
    }


}


