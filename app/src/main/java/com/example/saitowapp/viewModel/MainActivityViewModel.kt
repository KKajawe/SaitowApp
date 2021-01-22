package com.example.saitowapp.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.saitowapp.model.Data_Prefernce
import com.example.saitowapp.repository.DataPrefernceRepository
import com.example.saitowapp.repository.LoginRepository
import com.example.saitowapp.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivityViewModel(val applicationContext: Context, val loginRepo: LoginRepository) :
    ViewModel() {
    fun getLoginDetail(username: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepo.getLoginDetail(username, password)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun saveData(expireAt: Int, token: String, isLoggedIn: Boolean) {
        viewModelScope.launch {
            DataPrefernceRepository(applicationContext).setLoginData_Prefernce(
                expireAt,
                token,
                isLoggedIn
            )
        }
    }

    fun getPreferenceData(): Data_Prefernce {
        lateinit var loginData: Data_Prefernce
        runBlocking {
            try {
                loginData = DataPrefernceRepository(applicationContext).getLoginData.first()
            } catch (e: Exception) {
                loginData = Data_Prefernce(-1, "", false)
            }
        }
        return loginData
    }
}