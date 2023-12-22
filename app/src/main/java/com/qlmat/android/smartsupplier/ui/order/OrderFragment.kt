package com.qlmat.android.smartsupplier.ui.order

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_ID
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_IMAGE
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_NAME
import com.qlmat.android.smartsupplier.data.model.Warehouse
import com.qlmat.android.smartsupplier.data.state.OrderState
import com.qlmat.android.smartsupplier.data.state.WarehousesState
import com.qlmat.android.smartsupplier.databinding.FragmentOrderBinding
import com.qlmat.android.smartsupplier.ui.dialog.SuccessDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

enum class OrderOption {
    ADDRESS,
    WAREHOUSE
}

class OrderFragment : Fragment(R.layout.fragment_order), OnWarehouseClickListener {

    private val viewBinding: FragmentOrderBinding by viewBinding()
    private val viewModel: OrderViewModel by viewModel()
    private lateinit var warehousesAdapter: WarehousesAdapter

    private var quantity: Int = 1

    private var warehouseName: String = ""
    private var warehouseAddress: String = ""

    private var productName: String = ""
    private var productImage: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productName = arguments?.getString(PRODUCT_NAME) ?: ""
        productImage = arguments?.getString(PRODUCT_IMAGE) ?: ""

        initList()
        initObserver()
        initActions()

        viewModel.getWarehouses()
    }

    override fun onClick(warehouse: Warehouse) {
        warehouseName = warehouse.name
        warehouseAddress = warehouse.address
    }

    private fun initActions() = with(viewBinding) {
        imageViewOrderBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        buttonQuantityRemove.setOnClickListener {
            if (quantity != 1) {
                quantity--
                textViewOrderQuantity.text = quantity.toString()
            }
        }

        buttonQuantityAdd.setOnClickListener {
            quantity++
            textViewOrderQuantity.text = quantity.toString()
        }

        layoutOptionOrderByAddress.setOnClickListener {
            layoutOptionOrderByAddress.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_item_card_selected)
            layoutOptionPickUpFromWarehouse.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_item_card)

            showAddressLayout()
        }

        layoutOptionPickUpFromWarehouse.setOnClickListener {
            layoutOptionOrderByAddress.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_item_card)
            layoutOptionPickUpFromWarehouse.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_item_card_selected)

            showWarehouseLayout()
        }

        buttonConfirmOrder.setOnClickListener {
            val selectedOption = if (layoutOrderByAddress.isVisible) OrderOption.ADDRESS
                                 else OrderOption.WAREHOUSE

            val deliveryOption = if (selectedOption == OrderOption.ADDRESS) "Delivery to the address"
                                 else "Receiving from warehouse"

            val deliveryDetails = when (selectedOption) {
                OrderOption.ADDRESS -> editTextOrderAddress.text.toString()
                OrderOption.WAREHOUSE -> "$warehouseName: $warehouseAddress"
            }

            viewModel.toOrder(
                productName = productName,
                productImage = productImage,
                quantity = quantity,
                deliveryOption = deliveryOption,
                deliveryDetails = deliveryDetails
            )
        }

    }

    private fun initList() = with(viewBinding) {
        warehousesAdapter = WarehousesAdapter(resources)
        warehousesAdapter.setListener(this@OrderFragment)

        recyclerWarehouses.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = warehousesAdapter
        }
    }

    private fun initObserver() {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner, ::handleOrderState)
        viewModel.warehousesStateLiveData.observe(viewLifecycleOwner, ::handleWarehousesState)
    }

    private fun handleOrderState(orderState: OrderState) {
        when (orderState) {
            is OrderState.Failed -> {
                Toast.makeText(requireContext(), orderState.message, Toast.LENGTH_LONG).show()
            }
            is OrderState.Loading -> {}
            is OrderState.Success -> {
                val dialog = SuccessDialog()
                dialog.show(requireActivity().supportFragmentManager, "Order")
            }
        }
    }

    private fun handleWarehousesState(warehouseState: WarehousesState) = with(viewBinding) {
        when (warehouseState) {
            is WarehousesState.Failed -> {
                progressBarOrder.visibility = View.GONE
                layoutOrder.visibility = View.VISIBLE
                Toast.makeText(requireContext(), warehouseState.message, Toast.LENGTH_LONG).show()
            }
            is WarehousesState.Loading -> {
                progressBarOrder.visibility = View.VISIBLE
            }
            is WarehousesState.Success -> {
                progressBarOrder.visibility = View.GONE
                val warehouses = warehouseState.value
                warehousesAdapter.setData(warehouses)
                layoutOrder.visibility = View.VISIBLE
            }
        }
    }

    private fun showAddressLayout() = with(viewBinding) {
        layoutOrderByAddress.visibility = View.VISIBLE
        layoutPickUpFromWarehouse.visibility = View.GONE
    }

    private fun showWarehouseLayout() = with(viewBinding) {
        layoutOrderByAddress.visibility = View.GONE
        layoutPickUpFromWarehouse.visibility = View.VISIBLE
    }

}