package com.qlmat.android.smartsupplier.data.model

data class Product(
    val id: String,
    val category: String,
    val name: String,
    val supplier: String,
    val image: String,
    val rating: Float,
    val price: Double
)
