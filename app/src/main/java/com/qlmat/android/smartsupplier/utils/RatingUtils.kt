package com.qlmat.android.smartsupplier.utils

object RatingUtils {
    fun calculateRatingAbilityPercentage(selectedRating: Double, avgRating: Double): Double {
        return ((selectedRating - avgRating) / avgRating) * 100
    }
}