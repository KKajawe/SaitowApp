package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class PriceOperator(
    @SerializedName("gross")
    var gross: Double,
    @SerializedName("net")
    var net: Double,
    @SerializedName("tax")
    var tax: TaxOperator
)