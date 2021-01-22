package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Orders(
    @SerializedName("data")
    var `data`: ArrayList<DataOrders>,
    @SerializedName("meta")
    var meta: Meta
)