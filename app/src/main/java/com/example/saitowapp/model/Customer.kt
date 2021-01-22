package com.example.saitowapp.model


import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("addressCustomer")
    var addressCustomer: AddressCustomer,
    @SerializedName("benefit")
    var benefit: Double,
    @SerializedName("id")
    var id: Int,
    @SerializedName("priceCustomers")
    var priceCustomers: List<PriceCustomer>
)