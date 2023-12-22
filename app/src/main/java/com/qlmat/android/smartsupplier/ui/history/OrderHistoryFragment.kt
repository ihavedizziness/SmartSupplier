package com.qlmat.android.smartsupplier.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo.Companion.NO_VALUE
import com.qlmat.android.smartsupplier.data.state.OrderHistoryState
import com.qlmat.android.smartsupplier.databinding.FragmentOrderHistoryBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryFragment: Fragment(R.layout.fragment_order_history) {

    private val sharedPreferencesRepository: SharedPreferencesRepo by inject()
    private val viewBinding: FragmentOrderHistoryBinding by viewBinding()
    private val viewModel: OrderHistoryViewModel by viewModel()
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserver()

        val userId = sharedPreferencesRepository.getUserId()
        if (userId != NO_VALUE) {
            viewModel.getUserOrders(userId)
        }
    }

    private fun initList() = with(viewBinding) {
        ordersAdapter = OrdersAdapter(resources)

        recyclerOrders.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ordersAdapter
        }
    }

    private fun initObserver() {
        viewModel.orderHistoryLiveData.observe(viewLifecycleOwner, ::handleOrderHistoryState)
    }

    private fun handleOrderHistoryState(orderHistoryState: OrderHistoryState) = with(viewBinding) {
        when (orderHistoryState) {
            is OrderHistoryState.Failed -> {
                progressBarOrderHistory.visibility = View.GONE
                layoutOrderHistory.visibility = View.VISIBLE
                Toast.makeText(requireContext(), orderHistoryState.message, Toast.LENGTH_LONG).show()
            }
            is OrderHistoryState.Loading -> {
                progressBarOrderHistory.visibility = View.VISIBLE
            }
            is OrderHistoryState.Success -> {
                progressBarOrderHistory.visibility = View.GONE
                val orderHistory = orderHistoryState.value
                if (orderHistory.isEmpty()) {
                    textViewNoOrderHistory.visibility = View.VISIBLE
                }
                ordersAdapter.setData(orderHistory)
                layoutOrderHistory.visibility = View.VISIBLE
            }
        }
    }

}