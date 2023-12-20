package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.Warehouse

sealed class WarehouseState {

    data object Loading: WarehouseState()

    data class Failed(
        val message: String
    ): WarehouseState()

    data class Success(
        val value: List<Warehouse>
    ): WarehouseState()

}