package com.qlmat.android.smartsupplier.network.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.state.RegistrationState
import com.qlmat.android.smartsupplier.network.auth.AuthManager
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registrationStateLiveData = MutableLiveData<RegistrationState>()
    val registrationStateLiveData: LiveData<RegistrationState> get() = _registrationStateLiveData

    fun register(
        email: String,
        phoneNumber: String,
        password: String
    ) {
        _registrationStateLiveData.value = RegistrationState.Loading

        viewModelScope.launch {
            try {
                val (result, errorMessage) = AuthManager.register(
                    email,
                    phoneNumber,
                    password
                )
                if (result != null) {
                    _registrationStateLiveData.value =
                        RegistrationState.Success(result)
                } else {
                    _registrationStateLiveData.value =
                        RegistrationState.Failed(errorMessage ?: "Registration failed")
                }
            } catch (ex: Exception) {
                _registrationStateLiveData.value =
                    RegistrationState.Failed("An error occurred during registration")
            }
        }
    }
}