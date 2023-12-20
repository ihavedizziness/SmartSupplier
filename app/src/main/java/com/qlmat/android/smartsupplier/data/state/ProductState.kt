package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.Product

sealed class ProductState {

    data object Loading: ProductState()

    data class Failed(
        val message: String
    ): ProductState()

    data class Success(
        val value: Product
    ): ProductState()

}