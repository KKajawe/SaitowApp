package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("expire_at")
    var expireAt: Int?,
    @SerializedName("firebase_expire_at")
    var firebaseExpireAt: Int?,
    @SerializedName("firebase_token")
    var firebaseToken: String?,
    @SerializedName("force_password_reset")
    var forcePasswordReset: Boolean?,
    @SerializedName("token")
    var token: String?
)