package com.qlmat.android.smartsupplier.network.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

object AuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification(email, onComplete)
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    private fun sendEmailVerification(email: String, onComplete: (Boolean, String?) -> Unit) {
        auth.currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
        }
    }

    fun checkAuthState(onAuthStateChanged: (Boolean) -> Unit) {
        val currentUser = auth.currentUser
        onAuthStateChanged(currentUser != null)
    }

    fun signOut() {
        auth.signOut()
    }

}