package com.qlmat.android.smartsupplier.network.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.state.LoginState
import com.qlmat.android.smartsupplier.network.auth.AuthManager
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginStateLiveData = MutableLiveData<LoginState>()
    val loginStateLiveData: LiveData<LoginState> get() = _loginStateLiveData

    fun login(
        email: String,
        password: String
    ) {
        _loginStateLiveData.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val (success, errorMessage) = AuthManager.login(
                    email,
                    password
                )
                if (success) {
                    _loginStateLiveData.value = LoginState.Success
                } else {
                    _loginStateLiveData.value =
                        LoginState.Failed(errorMessage ?: "Login Failed")
                }
            } catch (ex: Exception) {
                _loginStateLiveData.value =
                    LoginState.Failed("An error occurred during sign in")
            }
        }
    }

}