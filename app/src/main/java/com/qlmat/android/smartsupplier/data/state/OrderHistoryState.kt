package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.Order

sealed class OrderHistoryState {

    data object Loading: OrderHistoryState()

    data class Failed(
        val message: String
    ): OrderHistoryState()

    data class Success(
        val value: List<Order>
    ): OrderHistoryState()

}
