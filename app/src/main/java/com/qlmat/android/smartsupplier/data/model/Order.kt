package com.qlmat.android.smartsupplier.data.model

data class Order(
    val userId: String,
    val productId: String,
    val quantity: Int,
    val deliveryOption: String,
    val deliveryDetails: String
) {
    fun toMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "productId" to productId,
        "quantity" to quantity,
        "deliveryOption" to deliveryOption,
        "deliveryDetails" to deliveryDetails
    )
}
