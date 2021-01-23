package com.example.saitowapp.repository

import androidx.paging.ItemKeyedDataSource
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.model.Orders
import com.example.saitowapp.network.ApiService
import com.example.saitowapp.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


class OrderDataSource(
    coroutineContext: CoroutineContext,
    val sort: String,
    val token: String
) : ItemKeyedDataSource<Int, DataOrders>() {
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    override fun getKey(item: DataOrders): Int {
        return item.id
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<DataOrders>
    ) {
        scope.launch {
            try {
                val response: Response<Orders> =
                    apiService.getOrders(token,0, params.requestedLoadSize, sort)
                when {
                    response.isSuccessful -> {
                        val ordersList = response.body() as Orders
                        callback.onResult(ordersList.data)
                    }
                    else -> {
                        Timber.tag("ResponseUnsuccess")
                            .e(response.message() ?: "Error in initial load!ing!")
                    }
                }

            } catch (exception: Exception) {
                Timber.tag("ExceptionOrderList")
                    .e(exception.message.toString() ?: "Exception : Failed to fetch data!")
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<DataOrders>) {
        scope.launch {
            try {
                val response: Response<Orders> =
                    apiService.getOrders(token,params.key - 1, params.requestedLoadSize, sort)
                when {
                    response.isSuccessful -> {
                        val ordersList = response.body() as Orders
                        callback.onResult(ordersList.data)
                    }
                    else -> {
                        Timber.tag("ResponseUnsuccess")
                            .e(response.message() ?: "Error in After loading!!")
                    }
                }

            } catch (exception: Exception) {
                Timber.tag("ExceptionOrderList")
                    .e(exception.message.toString() ?: "Exception : Failed to fetch data!")
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<DataOrders>) {

    }

}