package com.qlmat.android.smartsupplier.data.model

data class Order(
    val userId: String,
    val productName: String,
    val productImage: String,
    val quantity: Int,
    val deliveryOption: String,
    val deliveryDetails: String,
    val date: String
) {
    fun toMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "productName" to productName,
        "productImage" to productImage,
        "quantity" to quantity,
        "deliveryOption" to deliveryOption,
        "deliveryDetails" to deliveryDetails,
        "date" to date
    )
}
