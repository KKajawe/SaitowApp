package com.example.saitowapp.ui_classes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.saitowapp.R
import com.example.saitowapp.utility.Status
import com.example.saitowapp.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.frag_login.*

class LoginFragment : Fragment() {
    private val fragViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.frag_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            if (checkInput(edt_username.text.toString(), edt_password.text.toString())) {
                setUpObserver(edt_username.text.toString(), edt_password.text.toString())
            } else {
                Toast.makeText(activity, "Please Enter Valid Input!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkInput(username: String, password: String): Boolean =
        !(username.isNullOrBlank() || password.isNullOrBlank())

    private fun setUpObserver(username: String, password: String) {
        fragViewModel.getLoginDetail(username, password).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        if (resource.data?.code() == 200) {
                            fragViewModel.saveData(
                                resource.data.body()?.data?.expireAt!!,
                                resource.data.body()?.data?.token!!,
                                true
                            )
                            val fragmanger = activity?.supportFragmentManager
                            val fragTransaction = fragmanger?.beginTransaction()
                            fragTransaction?.replace(R.id.container, OrdersFragment())
                            fragTransaction?.commit()

                        } else
                            Toast.makeText(activity, "Login Unsuccessful!!", Toast.LENGTH_LONG)
                                .show()
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Log.d("abc", resource.message.toString())
                        Toast.makeText(activity, "Error: Login Unsuccessful!!", Toast.LENGTH_LONG)
                            .show()
                    }
                    Status.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}