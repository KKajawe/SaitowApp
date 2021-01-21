package com.example.saitowapp.network

import com.example.saitowapp.model.LoginModel
import okhttp3.Credentials
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST("operator/login")
    suspend fun getLoginDetails(@Header("Authorization") authorization:String): Response<LoginModel>


}