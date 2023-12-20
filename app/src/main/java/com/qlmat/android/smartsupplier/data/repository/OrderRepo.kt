package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.data.model.Order
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OrderRepo : KoinComponent {

    private val auth: FirebaseAuth by inject()
    private val firestore: FirebaseFirestore by inject()
    private val ordersCollection = firestore.collection("orders")

    suspend fun toOrder(
        productId: String,
        quantity: Int,
        deliveryOption: String,
        deliveryDetails: String
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val orderData = Order(
                userId = userId,
                productId = productId,
                quantity = quantity,
                deliveryOption = deliveryOption,
                deliveryDetails = deliveryDetails
            )

            ordersCollection.add(orderData.toMap()).await()
        } else {
            throw Exception("User not authenticated")
        }
    }

}