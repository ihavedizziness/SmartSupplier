package com.qlmat.android.smartsupplier.ui.product

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_ID
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_NAME
import com.qlmat.android.smartsupplier.arch.Constants.PRODUCT_IMAGE
import com.qlmat.android.smartsupplier.data.model.Product
import com.qlmat.android.smartsupplier.data.state.ProductState
import com.qlmat.android.smartsupplier.databinding.FragmentProductDetailBinding
import com.qlmat.android.smartsupplier.ui.compare.CompareFragment
import com.qlmat.android.smartsupplier.ui.order.OrderFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val viewBinding: FragmentProductDetailBinding by viewBinding()
    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var productRef: Product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initActions()

        arguments?.getString(PRODUCT_ID)?.let { viewModel.getProduct(it) }
    }

    private fun initActions() = with(viewBinding) {
        imageViewProductDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        buttonOrderProductDetail.setOnClickListener {
            val orderFragment = OrderFragment()
            orderFragment.arguments = Bundle().apply {
                putString(PRODUCT_ID, arguments?.getString(PRODUCT_ID))
                putString(PRODUCT_NAME, productRef.name)
                putString(PRODUCT_IMAGE, productRef.image)
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerView, orderFragment)
                .addToBackStack("Order")
                .commit()
        }

        buttonCompareProductDetail.setOnClickListener {
            val compareFragment = CompareFragment()
            compareFragment.arguments = Bundle().apply {
                putString(PRODUCT_ID, arguments?.getString(PRODUCT_ID))
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerView, compareFragment)
                .addToBackStack("Compare")
                .commit()
        }
    }

    private fun initObserver() {
        viewModel.productStateLiveData.observe(viewLifecycleOwner, ::handleProductState)
    }

    private fun handleProductState(productState: ProductState) = with(viewBinding) {
        when (productState) {
            is ProductState.Failed -> {
                progressBarProductDetail.visibility = View.GONE
                layoutProductDetail.visibility = View.VISIBLE
                Toast.makeText(requireContext(), productState.message, Toast.LENGTH_LONG).show()
            }
            is ProductState.Loading -> {
                progressBarProductDetail.visibility = View.VISIBLE
            }
            is ProductState.Success -> {
                progressBarProductDetail.visibility = View.GONE
                val product = productState.value
                productRef = product

                Glide.with(requireContext())
                    .load(product.image)
                    .centerCrop()
                    .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.product_image_radius)))
                    .error(R.drawable.ic_logo)
                    .into(imageViewProductDetail)

                textViewProductDetailPrice.text = "$${product.price}"
                textViewProductDetailCategory.text = product.category
                textViewProductDetailName.text = product.name
                textViewProductDetailDescription.text = product.description
                textViewProductDetailSupplier.text = product.supplier
                textViewProductDetailRating.text = product.rating.toString()

                textViewProductDetailAvailability.apply {
                    text = if (product.stockAvailability) "Yes" else "No"
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if (product.stockAvailability) R.color.green else R.color.red
                        )
                    )
                }

                layoutProductDetail.visibility = View.VISIBLE
            }
        }
    }

}