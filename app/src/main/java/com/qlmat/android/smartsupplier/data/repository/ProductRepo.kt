package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.qlmat.android.smartsupplier.data.model.Product
import kotlinx.coroutines.tasks.await

class ProductRepo(firestore: FirebaseFirestore) {

    private val productsCollection = firestore.collection("products")

    suspend fun getProducts(): List<Product> {
        return try {
            val querySnapshot = productsCollection.get().await()

            querySnapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)
            }
        } catch (ex: Exception) {
            emptyList()
        }
    }

}