package com.example.saitowapp.ui_classes

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.saitowapp.databinding.FragLoginBinding
import com.example.saitowapp.utility.Status
import com.example.saitowapp.viewModel.MainActivityViewModel
import timber.log.Timber

class LoginFragment : Fragment() {
    private val fragViewModel by activityViewModels<MainActivityViewModel>()
    private var _binding: FragLoginBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val inputMethodManager = activity?.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken, 0
            )
            if (checkInput(
                    binding.edtUsername.text.toString(),
                    binding.edtPassword.text.toString()
                )
            ) {
                setUpObserver(
                    binding.edtUsername.text.toString(),
                    binding.edtPassword.text.toString()
                )
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
                        binding.progressBar.visibility = View.GONE
                        if (resource.data?.code() == 200) {
                            resource.data.body()?.data?.expireAt?.let { it1 ->
                                resource.data.body()?.data?.token?.let { it2 ->
                                    fragViewModel.saveData(
                                        it1,
                                        it2,
                                        true
                                    )
                                }
                            }
                            activity?.supportFragmentManager?.let { it1 ->
                                MainActivity().onChangeNavigation(
                                    it1, OrdersFragment()
                                )
                            }

                        } else
                            Toast.makeText(activity, "Login Unsuccessful!!", Toast.LENGTH_LONG)
                                .show()
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Timber.e(resource.message.toString() ?: "Error Occurred!")
                        Toast.makeText(activity, "Error: Login Unsuccessful!!", Toast.LENGTH_LONG)
                            .show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
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