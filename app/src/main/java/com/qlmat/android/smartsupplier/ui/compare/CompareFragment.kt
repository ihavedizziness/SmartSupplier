package com.qlmat.android.smartsupplier.ui.compare

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_ID
import com.qlmat.android.smartsupplier.data.model.Product
import com.qlmat.android.smartsupplier.data.state.CompareState
import com.qlmat.android.smartsupplier.databinding.FragmentCompareBinding
import com.qlmat.android.smartsupplier.ui.home.OnProductClickListener
import com.qlmat.android.smartsupplier.ui.home.ProductsAdapter
import com.qlmat.android.smartsupplier.ui.product.ProductDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompareFragment : Fragment(R.layout.fragment_compare), OnProductClickListener {

    private val viewBinding: FragmentCompareBinding by viewBinding()
    private val viewModel: CompareViewModel by viewModel()
    private lateinit var similarProductsAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initActions()
        initObserver()

        arguments?.getString(PRODUCT_ID)?.let { viewModel.getPeerComparison(it) }
    }

    override fun onClick(product: Product) {
        val fragment = ProductDetailFragment()
        fragment.arguments = Bundle().apply {
            putString(PRODUCT_ID, product.id)
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, fragment)
            .addToBackStack("Product Details")
            .commit()
    }

    private fun initList() = with(viewBinding) {
        similarProductsAdapter = ProductsAdapter(resources)
        similarProductsAdapter.setListener(this@CompareFragment)

        recyclerSimilarProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = similarProductsAdapter
        }
    }

    private fun initActions() = with(viewBinding) {
        imageViewCompareBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initObserver() {
        viewModel.compareStateLiveData.observe(viewLifecycleOwner, ::handleCompareState)
    }

    private fun handleCompareState(compareState: CompareState) = with(viewBinding) {
        when (compareState) {
            is CompareState.Failed -> {
                progressBarCompare.visibility = View.GONE
                layoutCompare.visibility = View.VISIBLE
                Toast.makeText(requireContext(), compareState.message, Toast.LENGTH_LONG).show()
            }
            is CompareState.Loading -> {
                progressBarCompare.visibility = View.VISIBLE
            }
            is CompareState.Success -> {
                progressBarCompare.visibility = View.GONE
                val similarProducts = compareState.similarProducts
                similarProductsAdapter.setData(similarProducts)

                val costBenefit = String.format("%.2f", compareState.costBenefitPercentage) + "%"
                val ratingAbility = String.format("%.2f", compareState.ratingabilityPercentage) + "%"

                textViewCompareCostBenefit.text = costBenefit
                textViewCompareRatingAbility.text = ratingAbility
                layoutCompare.visibility = View.VISIBLE
            }
        }
    }

}