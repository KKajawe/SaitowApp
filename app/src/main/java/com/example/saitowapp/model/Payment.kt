package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("transaction")
    var transaction: Transaction
)