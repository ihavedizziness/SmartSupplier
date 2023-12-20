package com.qlmat.android.smartsupplier.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.model.Order
import com.qlmat.android.smartsupplier.data.model.Warehouse
import com.qlmat.android.smartsupplier.data.repository.OrderRepo
import com.qlmat.android.smartsupplier.data.repository.WarehouseRepo
import com.qlmat.android.smartsupplier.data.state.OrderState
import com.qlmat.android.smartsupplier.data.state.WarehouseState
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepo: OrderRepo,
    private val warehouseRepo: WarehouseRepo
) : ViewModel() {

    private val _orderStateLiveData = MutableLiveData<OrderState>()
    val orderStateLiveData: LiveData<OrderState> get() = _orderStateLiveData

    private val _warehousesStateLiveData = MutableLiveData<WarehouseState>()
    val warehousesStateLiveData: LiveData<WarehouseState> get() = _warehousesStateLiveData

    fun toOrder(
        productId: String,
        quantity: Int,
        deliveryOption: String,
        deliveryDetails: String
    ) {
        _orderStateLiveData.value = OrderState.Loading

        viewModelScope.launch {
            try {
                orderRepo.toOrder(
                    productId = productId,
                    quantity = quantity,
                    deliveryOption = deliveryOption,
                    deliveryDetails = deliveryDetails
                )
                _orderStateLiveData.value = OrderState.Success
            } catch (ex: Exception) {
                _orderStateLiveData.value = ex.message?.let { OrderState.Failed(it) }
            }
        }
    }

    fun getWarehouses() {
        _warehousesStateLiveData.value = WarehouseState.Loading

        viewModelScope.launch {
            try {
                val warehouses = warehouseRepo.getWarehouses()
                _warehousesStateLiveData.value = WarehouseState.Success(warehouses)
            } catch (ex: Exception) {
                _warehousesStateLiveData.value = WarehouseState.Failed("Failed to fetch warehouses")
            }
        }
    }

}