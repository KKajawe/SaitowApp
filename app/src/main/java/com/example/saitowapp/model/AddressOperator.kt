package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class AddressOperator(
    @SerializedName("city")
    var city: String,
    @SerializedName("company")
    var company: String,
    @SerializedName("country")
    var country: Int,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("floor")
    var floor: Int,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("postcode")
    var postcode: String,
    @SerializedName("salutation")
    var salutation: Int,
    @SerializedName("street")
    var street: String
)