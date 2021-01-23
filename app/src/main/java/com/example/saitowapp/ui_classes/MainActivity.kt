package com.example.saitowapp.ui_classes

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.saitowapp.R
import com.example.saitowapp.databinding.ActivityMainBinding
import com.example.saitowapp.factory.ViewModelFactory
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.network.RetrofitBuilder
import com.example.saitowapp.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.mtoolbar)
        setUpViewModel()
        if (viewModel.getPreferenceData().isLoggedIn) {
            onChangeNavigation(supportFragmentManager, OrdersFragment())
        } else {
            onChangeNavigation(supportFragmentManager, LoginFragment())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return viewModel.getPreferenceData().isLoggedIn

    }


    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                applicationContext,
                RetrofitBuilder.getRetrofit().create(ApiService::class.java)
            )
        ).get(MainActivityViewModel::class.java)
    }

    fun onChangeNavigation(fragmanger: FragmentManager, fragment: Fragment) {
        val fragTransaction = fragmanger.beginTransaction()
        fragTransaction.add(R.id.container, fragment)
        fragTransaction.commit()
    }
}