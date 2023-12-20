package com.qlmat.android.smartsupplier.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.repository.ProductRepo
import com.qlmat.android.smartsupplier.data.state.ProductsState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _productsStateLiveData = MutableLiveData<ProductsState>()
    val productsLiveData: LiveData<ProductsState> get() = _productsStateLiveData

    fun getProducts() {
        _productsStateLiveData.value = ProductsState.Loading

        viewModelScope.launch {
            try {
                val products = productRepo.getProducts()
                _productsStateLiveData.value = ProductsState.Success(products)
            } catch (ex: Exception) {
                _productsStateLiveData.value = ProductsState.Failed("Failed to fetch products")
            }
        }
    }

}