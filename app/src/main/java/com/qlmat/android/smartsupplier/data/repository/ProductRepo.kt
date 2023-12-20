package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.data.model.Product
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductRepo : KoinComponent {

    private val firestore: FirebaseFirestore by inject()
    private val productsCollection = firestore.collection("products")

    suspend fun getProducts(): List<Product> = try {
        val querySnapshot = productsCollection.get().await()

        querySnapshot.documents.mapNotNull { document ->
            val data = document.data
            data?.let {
                Product(
                    id = document.id,
                    category = it["category"] as? String ?: "",
                    image = it["image"] as? String ?: "",
                    name = it["name"] as? String ?: "",
                    price = (it["price"] as? Number)?.toDouble() ?: 0.0,
                    rating = (it["rating"] as? Number)?.toDouble() ?: 0.0,
                    supplier = it["supplier"] as? String ?: "",
                    description = it["description"] as? String ?: "",
                    stockAvailability = it["stockAvailability"] as? Boolean ?: false
                )
            }
        }
    } catch (ex: Exception) {
        emptyList()
    }

    suspend fun getProductById(productId: String): Product? {
        return try {
            val documentSnapshot = productsCollection.document(productId).get().await()

            documentSnapshot.takeIf { it.exists() }?.data?.let { data ->
                return Product(
                    id = documentSnapshot.id,
                    category = data["category"] as? String ?: "",
                    image = data["image"] as? String ?: "",
                    name = data["name"] as? String ?: "",
                    price = (data["price"] as? Number)?.toDouble() ?: 0.0,
                    rating = (data["rating"] as? Number)?.toDouble() ?: 0.0,
                    supplier = data["supplier"] as? String ?: "",
                    description = data["description"] as? String ?: "",
                    stockAvailability = data["stockAvailability"] as? Boolean ?: false
                )
            }
            null
        } catch (ex: Exception) {
            null
        }
    }

}
