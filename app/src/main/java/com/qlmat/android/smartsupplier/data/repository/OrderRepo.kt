package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo.Companion.NO_VALUE
import com.qlmat.android.smartsupplier.data.model.Order
import com.qlmat.android.smartsupplier.utils.DateUtils
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OrderRepo : KoinComponent {

    private val sharedPreferencesRepository: SharedPreferencesRepo by inject()
    private val firestore: FirebaseFirestore by inject()
    private val ordersCollection = firestore.collection("orders")

    suspend fun toOrder(
        productName: String,
        productImage: String,
        quantity: Int,
        deliveryOption: String,
        deliveryDetails: String
    ) {
        val userId = sharedPreferencesRepository.getUserId()
        if (userId != NO_VALUE) {
            val orderData = Order(
                userId = userId,
                productName = productName,
                productImage = productImage,
                quantity = quantity,
                deliveryOption = deliveryOption,
                deliveryDetails = deliveryDetails,
                date = DateUtils.formatDate(System.currentTimeMillis())
            )

            ordersCollection.add(orderData.toMap()).await()
        } else {
            throw Exception("User not authenticated")
        }
    }

    suspend fun getUserOrders(userId: String): List<Order> = try {
        val querySnapshot = ordersCollection
            .whereEqualTo("userId", userId)
            .get()
            .await()

        querySnapshot.documents.mapNotNull { document ->
            val data = document.data
            data?.let {
                Order(
                    userId = it["userId"] as? String ?: "",
                    productName = it["productName"] as? String ?: "",
                    productImage = it["productImage"] as? String ?: "",
                    quantity = (it["quantity"] as? Number)?.toInt() ?: 0,
                    deliveryOption = it["deliveryOption"] as? String ?: "",
                    deliveryDetails = it["deliveryDetails"] as? String ?: "",
                    date = it["date"] as? String ?: ""
                )
            }
        }
    } catch (ex: Exception) {
        emptyList()
    }

}