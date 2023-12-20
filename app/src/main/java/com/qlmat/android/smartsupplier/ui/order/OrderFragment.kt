package com.qlmat.android.smartsupplier.ui.order

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.databinding.FragmentOrderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment(R.layout.fragment_order) {

    private val viewBinding: FragmentOrderBinding by viewBinding()
    private val viewModel: OrderViewModel by viewModel()
    private lateinit var warehousesAdapter: WarehousesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActions()
    }

    private fun initActions() = with(viewBinding) {
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