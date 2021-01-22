package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class DataOrders(
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("currency")
    var currency: Currency,
    @SerializedName("customer")
    var customer: Customer,
    @SerializedName("id")
    var id: Int,
    @SerializedName("operator")
    var `operator`: Operator,
    @SerializedName("payment")
    var payment: Payment,
    @SerializedName("state")
    var state: Int,
    @SerializedName("sum_original_price")
    var sumOriginalPrice: Double,
    @SerializedName("updated_at")
    var updatedAt: String
)