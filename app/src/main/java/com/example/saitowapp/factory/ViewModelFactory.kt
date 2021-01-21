package com.example.saitowapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.repository.LoginRepository
import com.example.saitowapp.viewModel.MainActivityViewModel


class ViewModelFactory(val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(LoginRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}