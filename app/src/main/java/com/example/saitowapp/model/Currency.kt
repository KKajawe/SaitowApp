package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("code")
    var code: String,
    @SerializedName("factor")
    var factor: Double,
    @SerializedName("id")
    var id: Int
)