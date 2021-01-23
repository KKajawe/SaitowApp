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
import com.example.saitowapp.databinding.FragOrderlistBinding
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.utility.Status
import com.example.saitowapp.viewModel.MainActivityViewModel

class OrdersFragment : Fragment() {
    private var _binding: FragOrderlistBinding? = null
    private val binding get() = _binding!!
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
    ): View? {
        _binding = FragOrderlistBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mprogressBar.visibility = View.VISIBLE
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getOrderList().observe(viewLifecycleOwner, orderListObserver)
        binding.ordersListView.adapter = adapterOrder
        binding.mprogressBar.visibility = View.GONE

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.saveData(-1, "", false)
                activity?.supportFragmentManager?.let {
                    MainActivity().onChangeNavigation(
                        it,
                        LoginFragment()
                    )
                }
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
                    activity?.supportFragmentManager?.let {
                        customDialog.showDialogWithAction(View.OnClickListener {
                            viewModel.getOrderList().removeObserver(orderListObserver)
                            setFilterObserver()
                            customDialog.dismiss()
                            viewModel.isFilter = true
                            item.icon =
                                ContextCompat.getDrawable(requireContext(), R.drawable.filtersort)
                        }, it)
                    }

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
                        binding.mprogressBar.visibility = View.GONE
                        resource.data?.body()?.let { it ->
                            adapterOrdersFilter = OrderFilterListAdapter(it)
                            binding.ordersListView.adapter = adapterOrdersFilter
                        }
                    }
                    Status.ERROR -> {
                        binding.mprogressBar.visibility = View.GONE
                        Toast.makeText(context, "Unable to load filter Data!!", Toast.LENGTH_LONG)
                            .show()
                    }
                    Status.LOADING -> {
                        binding.mprogressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


