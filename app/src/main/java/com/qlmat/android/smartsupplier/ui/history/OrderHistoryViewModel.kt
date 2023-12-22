package com.qlmat.android.smartsupplier.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qlmat.android.smartsupplier.data.repository.OrderRepo
import com.qlmat.android.smartsupplier.data.state.OrderHistoryState
import kotlinx.coroutines.launch

class OrderHistoryViewModel(
    private val orderRepo: OrderRepo
) : ViewModel() {

    private val _orderHistoryLiveData = MutableLiveData<OrderHistoryState>()
    val orderHistoryLiveData: LiveData<OrderHistoryState> get() = _orderHistoryLiveData

    fun getUserOrders(userId: String) {
        _orderHistoryLiveData.value = OrderHistoryState.Loading

        viewModelScope.launch {
            try {
                val myOrders = orderRepo.getUserOrders(userId)
                _orderHistoryLiveData.value = OrderHistoryState.Success(myOrders)
            } catch (ex: Exception) {
                _orderHistoryLiveData.value = OrderHistoryState.Failed("Failed to fetch your order history")
            }
        }
    }

}