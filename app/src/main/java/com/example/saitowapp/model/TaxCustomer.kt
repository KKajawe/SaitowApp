package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class TaxCustomer(
    @SerializedName("rate")
    var rate: Double,
    @SerializedName("type")
    var type: String,
    @SerializedName("value")
    var value: Double
)