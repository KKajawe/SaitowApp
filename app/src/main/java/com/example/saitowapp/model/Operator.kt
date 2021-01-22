package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Operator(
    @SerializedName("addressCustomer")
    var address: AddressOperator,
    @SerializedName("benefit")
    var benefit: Double,
    @SerializedName("id")
    var id: Int,
    @SerializedName("prices")
    var prices: List<PriceOperator>
)