package com.qlmat.android.smartsupplier.utils

object PriceUtils {

    fun calculateCostBenefit(selectedPrice: Double, avgPrice: Double): Double {
        return ((avgPrice - selectedPrice) / avgPrice) * 100
    }

}