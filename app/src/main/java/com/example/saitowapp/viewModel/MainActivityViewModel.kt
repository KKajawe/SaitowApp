package com.example.saitowapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.saitowapp.model.DataOrders
import com.example.saitowapp.model.Data_Prefernce
import com.example.saitowapp.repository.DataPrefernceRepository
import com.example.saitowapp.repository.LoginRepository
import com.example.saitowapp.repository.OrderDataSource
import com.example.saitowapp.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class MainActivityViewModel(val applicationContext: Context, val loginRepo: LoginRepository) :
    ViewModel() {
    private var ordersLiveData: LiveData<PagedList<DataOrders>>
    private var sortAscOrdersLiveData: LiveData<PagedList<DataOrders>>
    private var sortDscOrdersLiveData: LiveData<PagedList<DataOrders>>
    private var filterOrdersLiveData: LiveData<PagedList<DataOrders>>

    var sortParam: String = ""
    var filterStartTime: String = ""
    var filterEndTime :String=""
    var isFilter :Boolean = false

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        ordersLiveData = initializedPagedListBuilder(config).build()
        sortAscOrdersLiveData = initializedPagedListBuilder(config).build()
        sortDscOrdersLiveData = initializedPagedListBuilder(config).build()
        filterOrdersLiveData = initializedPagedListBuilder(config).build()
    }

    fun getLoginDetail(username: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepo.getLoginDetail(username, password)))
        } catch (exception: Exception) {
            Timber.e(exception.message ?: "Error Occurred!")
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
                Timber.e(e.message ?: "Error Occurred!")
            }
        }
        return loginData
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, DataOrders> {

        val dataSourceFactory = object : DataSource.Factory<Int, DataOrders>() {
            override fun create(): DataSource<Int, DataOrders> {
                var token: String = getPreferenceData().token
                return OrderDataSource(Dispatchers.IO, sortParam,token)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun getOrderList(): LiveData<PagedList<DataOrders>> {
        when (sortParam) {
            "+created_at" -> {
                return sortAscOrdersLiveData
            }
            "-created_at" -> {
                return sortDscOrdersLiveData
            }
            else -> {
                return ordersLiveData
            }
        }
    }

    fun getFilterList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepo.getfilterOrderList(getPreferenceData().token,getFilterParam(),sortParam)))
        } catch (exception: Exception) {
            Timber.e(exception.message ?: "Error Occurred!")
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    private fun getFilterParam(): String{
        var filterStr = ""
        if (filterStartTime.equals(""))
            filterStr = "lt;" + filterEndTime
        else if (filterEndTime.equals(""))
            filterStr = "gt;" + filterStartTime
        else
            filterStr = "gt;" + filterStartTime + "&filter[created_at]=lt;" + filterEndTime
        return filterStr
    }


}