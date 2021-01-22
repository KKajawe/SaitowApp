package com.example.saitowapp.network

import com.example.saitowapp.model.LoginModel
import com.example.saitowapp.model.Orders

import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST("operator/login")
    suspend fun getLoginDetails(@Header("Authorization") authorization:String): Response<LoginModel>

    @Headers("X-AUTH-TOKEN:ODE3Y2RhZjM3NTAxZDZhNjg5YmIwODQ0NWY1ZjZjMmM3NWVhMDQxNDU5NzI0NGJhODY5ZWFjYTU2ZjMzMmJkZDQzODU3ZTc3ZDIxYzI0MTA0YmM3MzllOTdiYWQ3YjFiODYyOWRmNWRlYTA3NzI=")
    @GET("operator/orders")
    suspend fun getOrders( @Query("offset") last_Item_id: Int,
                  @Query("limit") count_To_display: Int): Response<Orders>


}