package com.qlmat.android.smartsupplier.ui.compare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.repository.ProductRepo
import com.qlmat.android.smartsupplier.data.state.CompareState
import com.qlmat.android.smartsupplier.utils.PriceUtils
import com.qlmat.android.smartsupplier.utils.RatingUtils
import kotlinx.coroutines.launch

class CompareViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _compareStateLiveData = MutableLiveData<CompareState>()
    val compareStateLiveData: LiveData<CompareState> get() = _compareStateLiveData

    fun getPeerComparison(productId: String) {
        _compareStateLiveData.value = CompareState.Loading

        viewModelScope.launch {
            try {
                val selectedProduct = productRepo.getProductById(productId) ?: run {
                    _compareStateLiveData.value = CompareState.Failed("Product not found")
                    return@launch
                }

                val similarProducts = productRepo.getSimilarProducts(productId)

                if (similarProducts.isNotEmpty()) {
                    val avgPriceOfSimilarProducts = similarProducts.map { it.price }.average()
                    val costBenefitPercentage = PriceUtils.calculateCostBenefit(
                        selectedProduct.price, avgPriceOfSimilarProducts
                    )

                    val avgRatingOfSimilarProducts = similarProducts.map { it.rating }.average()
                    val ratingAbilityPercentage = RatingUtils.calculateRatingAbilityPercentage(
                        selectedProduct.rating, avgRatingOfSimilarProducts
                    )

                    _compareStateLiveData.value = CompareState.Success(
                        similarProducts,
                        costBenefitPercentage,
                        ratingAbilityPercentage
                    )
                } else {
                    _compareStateLiveData.value = CompareState.Failed("No similar products found")
                }
            } catch (ex: Exception) {
                _compareStateLiveData.value = ex.message?.let { CompareState.Failed(it) }
            }
        }
    }

}