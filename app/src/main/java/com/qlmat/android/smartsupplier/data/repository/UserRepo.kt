package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.data.model.User
import kotlinx.coroutines.tasks.await

class UserRepo(firestore: FirebaseFirestore) {

    private val usersCollection = firestore.collection("users")

    suspend fun addUser(userId: String, user: User) {
        usersCollection.document(userId).set(user).await()
    }

    suspend fun getUser(userId: String): User? {
        return try {
            val documentSnapshot = usersCollection.document(userId).get().await()
            documentSnapshot.toObject(User::class.java)
        } catch (ex: Exception) {
            null
        }
    }

}