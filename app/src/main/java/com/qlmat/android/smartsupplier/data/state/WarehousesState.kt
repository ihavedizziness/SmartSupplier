package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.Warehouse

sealed class WarehousesState {

    data object Loading: WarehousesState()

    data class Failed(
        val message: String
    ): WarehousesState()

    data class Success(
        val value: List<Warehouse>
    ): WarehousesState()

}