package com.example.saitowapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.repository.LoginRepository
import com.example.saitowapp.viewModel.MainActivityViewModel


class ViewModelFactory(val applicationContext: Context, val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(applicationContext,LoginRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}