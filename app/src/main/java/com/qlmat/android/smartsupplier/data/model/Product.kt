package com.qlmat.android.smartsupplier.data.model

data class Product(
    val id: String,
    val category: String,
    val name: String,
    val description: String,
    val stockAvailability: Boolean,
    val image: String,
    val rating: Double,
    val price: Double,
    val supplier: String
)
