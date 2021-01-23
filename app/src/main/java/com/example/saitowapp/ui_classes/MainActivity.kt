package com.example.saitowapp.ui_classes

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.saitowapp.R
import com.example.saitowapp.factory.ViewModelFactory
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.network.RetrofitBuilder
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