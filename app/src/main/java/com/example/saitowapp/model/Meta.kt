package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("count")
    var count: Int,
    @SerializedName("limit")
    var limit: Int
)