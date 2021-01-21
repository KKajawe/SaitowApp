package com.example.saitowapp.repository

import com.example.saitowapp.model.LoginModel
import com.example.saitowapp.network.ApiService
import okhttp3.Credentials

class LoginRepository(private val apiService:ApiService) {

    suspend fun getLoginDetail(username: String, password: String): retrofit2.Response<LoginModel> {
        val credentials: String = Credentials.basic(username, password)
        return apiService.getLoginDetails(credentials)
    }
}