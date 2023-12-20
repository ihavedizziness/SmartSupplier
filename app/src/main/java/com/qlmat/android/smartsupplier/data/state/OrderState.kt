package com.qlmat.android.smartsupplier.data.state

sealed class OrderState {

    data object Loading: OrderState()

    data class Failed(
        val message: String
    ): OrderState()

    data object Success: OrderState()

}
