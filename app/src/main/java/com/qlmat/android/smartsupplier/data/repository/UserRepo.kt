package com.qlmat.android.smartsupplier.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.data.model.User
import kotlinx.coroutines.tasks.await

class UserRepo(firestore: FirebaseFirestore) {

    private val usersCollection = firestore.collection("users")

    suspend fun addUser(userId: String, user: User) {
        usersCollection.document(userId).set(user).await()
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return try {
            val querySnapshot = usersCollection
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                User(
                    id = document.id,
                    email = document["email"] as? String ?: "",
                    phoneNumber = document["phoneNumber"] as? String ?: "",
                    password = document["password"] as? String ?: ""
                )
            } else {
                null
            }
        } catch (ex: Exception) {
            null
        }
    }


}