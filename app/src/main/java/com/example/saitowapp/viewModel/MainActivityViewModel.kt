package com.example.saitowapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.saitowapp.repository.LoginRepository
import com.example.saitowapp.utility.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(val loginRepo: LoginRepository) : ViewModel() {
    fun getLoginDetail(username: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepo.getLoginDetail(username, password)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}