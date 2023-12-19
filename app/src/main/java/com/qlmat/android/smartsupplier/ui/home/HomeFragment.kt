package com.qlmat.android.smartsupplier.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.data.model.Product
import com.qlmat.android.smartsupplier.data.state.ProductsState
import com.qlmat.android.smartsupplier.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home), OnProductClickListener {

    private val viewBinding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation()
        initActions()
        initList()
        initObserver()

        viewModel.getProducts()
    }

    override fun onClick(product: Product) {

    }

    private fun initList() = with(viewBinding) {
        productsAdapter = ProductsAdapter(resources)
        productsAdapter.setListener(this@HomeFragment)

        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = productsAdapter
        }
    }

    private fun initActions() = with(viewBinding) {

    }

    private fun initObserver() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, ::handleProductsState)
    }

    private fun handleProductsState(productsState: ProductsState) {
        when (productsState) {
            is ProductsState.Failed -> {
                Toast.makeText(requireContext(), productsState.message, Toast.LENGTH_LONG).show()
            }
            is ProductsState.Loading -> {

            }
            is ProductsState.Success -> {
                val products = productsState.value
                productsAdapter.setData(products)
            }
        }
    }

    private fun initNavigation() = with(viewBinding) {

    }

}