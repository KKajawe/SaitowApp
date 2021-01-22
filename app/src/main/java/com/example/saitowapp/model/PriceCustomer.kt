package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class PriceCustomer(
    @SerializedName("gross")
    var gross: Double,
    @SerializedName("net")
    var net: Double,
    @SerializedName("taxCustomer")
    var taxCustomer: TaxCustomer
)