package com.qlmat.android.smartsupplier.data.state

sealed class LoginState {

    data object Loading: LoginState()

    data class Failed(
        val message: String
    ): LoginState()

    data object Success: LoginState()

}
