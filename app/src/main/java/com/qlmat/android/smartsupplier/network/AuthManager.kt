package com.qlmat.android.smartsupplier.network

import com.google.firebase.auth.FirebaseAuth
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo.Companion.NO_VALUE
import com.qlmat.android.smartsupplier.data.model.User
import com.qlmat.android.smartsupplier.data.repository.UserRepo
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AuthManager : KoinComponent {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sharedPreferencesRepository: SharedPreferencesRepo by inject()
    private val userRepository: UserRepo by inject()

    suspend fun register(
        email: String,
        phoneNumber: String,
        password: String,
    ): Pair<User?, String?> {
        return try {
            val registerResult = auth.createUserWithEmailAndPassword(email, password).await()
            sendEmailVerification()
            val userId = registerResult.user?.uid
            userId?.let { uid ->
                with(sharedPreferencesRepository) {
                    setUserId(uid)
                    setUserEmail(email)
                    setUserPhoneNumber(phoneNumber)
                    setUserPassword(password)
                }
                val user = User(
                    id = uid,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber
                )
                userRepository.addUser(uid, user)
                Pair(user, null)
            } ?: Pair(null, "UID is null")
        } catch (ex: Exception) {
            Pair(null, ex.message)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Pair<Boolean, String?> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val user = userRepository.getUserByEmailAndPassword(email, password)
            user?.let {
                with(sharedPreferencesRepository) {
                    setUserId(it.id)
                    setUserEmail(it.email)
                    setUserPhoneNumber(it.phoneNumber)
                    setUserPassword(it.password)
                }
            }
            Pair(true, null)
        } catch (ex: Exception) {
            Pair(false, ex.message)
        }
    }


    private fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
    }

    fun logOut() {
        auth.signOut()
    }

}