package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("data")
    var `data`: Data?
)