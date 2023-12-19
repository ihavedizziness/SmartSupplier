package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.Product

sealed class ProductsState {

    data object Loading: ProductsState()

    data class Failed(
        val message: String
    ): ProductsState()

    data class Success(
        val value: List<Product>
    ): ProductsState()

}