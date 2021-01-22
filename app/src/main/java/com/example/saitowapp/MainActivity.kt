package com.example.saitowapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.saitowapp.factory.ViewModelFactory
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.network.RetrofitBuilder
import com.example.saitowapp.ui_classes.LoginFragment
import com.example.saitowapp.ui_classes.OrdersFragment
import com.example.saitowapp.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mtoolbar)
        setUpViewModel()
        if (viewModel.getPreferenceData().isLoggedIn) {
            val fragmanger = supportFragmentManager
            val fragTransaction = fragmanger.beginTransaction()
            fragTransaction.add(R.id.container, OrdersFragment())
            fragTransaction.commit()
        } else {
            val fragmanger = supportFragmentManager
            val fragTransaction = fragmanger.beginTransaction()
            fragTransaction.add(R.id.container, LoginFragment())
            fragTransaction.commit()
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
}