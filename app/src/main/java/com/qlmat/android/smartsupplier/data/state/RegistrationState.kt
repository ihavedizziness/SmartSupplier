package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.User

sealed class RegistrationState {

    data object Loading: RegistrationState()

    data class Failed(
        val message: String
    ): RegistrationState()

    data class Success(
        val value: User
    ): RegistrationState()

}
