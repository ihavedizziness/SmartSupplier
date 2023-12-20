package com.qlmat.android.smartsupplier.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.repository.ProductRepo
import com.qlmat.android.smartsupplier.data.state.ProductState
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _productStateLiveData = MutableLiveData<ProductState>()
    val productStateLiveData: LiveData<ProductState> get() = _productStateLiveData

    fun getProduct(productId: String) {
        _productStateLiveData.value = ProductState.Loading

        viewModelScope.launch {
            try {
                val product = productRepo.getProductById(productId)
                _productStateLiveData.value = product?.let { ProductState.Success(it) }
            } catch (ex: Exception) {
                _productStateLiveData.value = ProductState.Failed("Failed to fetch product")
            }
        }
    }

}