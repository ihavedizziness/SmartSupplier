package com.qlmat.android.smartsupplier.data.state

import com.qlmat.android.smartsupplier.data.model.Product

sealed class CompareState {

    data object Loading: CompareState()

    data class Failed(
        val message: String
    ): CompareState()

    data class Success(
        val similarProducts: List<Product>,
        val costBenefitPercentage: Double,
        val ratingabilityPercentage: Double
    ): CompareState()

}
