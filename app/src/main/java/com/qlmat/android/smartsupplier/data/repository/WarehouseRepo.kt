package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.data.model.Warehouse
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WarehouseRepo : KoinComponent {

    private val firestore: FirebaseFirestore by inject()
    private val warehousesCollection = firestore.collection("warehouses")

    suspend fun getWarehouses(): List<Warehouse> = try {
        val querySnapshot = warehousesCollection.get().await()

        querySnapshot.documents.mapNotNull { document ->
            val data = document.data
            data?.let {
                Warehouse(
                    id = document.id,
                    name = it["name"] as? String ?: "",
                    address = it["address"] as? String ?: ""
                )
            }
        }
    } catch (ex: Exception) {
        emptyList()
    }

}