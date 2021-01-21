package com.example.saitowapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.saitowapp.factory.ViewModelFactory
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.network.RetrofitBuilder
import com.example.saitowapp.ui_classes.LoginFragment
import com.example.saitowapp.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        onChangeNavigation(LoginFragment())

    }

    fun onChangeNavigation(fragment: Fragment) {
        val fragmanger = supportFragmentManager
        val fragTransaction = fragmanger.beginTransaction()
        fragTransaction.add(R.id.container, fragment)
        fragTransaction.commit()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(RetrofitBuilder.getRetrofit().create(ApiService::class.java))
        ).get(MainActivityViewModel::class.java)
    }
}