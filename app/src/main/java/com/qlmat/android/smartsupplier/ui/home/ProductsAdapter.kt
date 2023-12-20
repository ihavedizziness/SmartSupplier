package com.qlmat.android.smartsupplier.ui.home

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.data.model.Product
import com.qlmat.android.smartsupplier.databinding.ItemProductBinding

class ProductsAdapter(
    private val resources: Resources
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var products = listOf<Product>()
    private var listener: OnProductClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductsViewHolder {
        val itemBinding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: ProductsAdapter.ProductsViewHolder,
        position: Int
    ) {
        val product: Product = products[position]
        holder.bind(product, listener)
    }

    override fun getItemCount(): Int = products.size

    fun setData(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    fun setListener(listener: OnProductClickListener) {
        this.listener = listener
    }

    inner class ProductsViewHolder(
        private val itemBinding: ItemProductBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product, listener: OnProductClickListener?) = with(itemBinding) {
            Log.d("ProductsViewHolder", "Binding product: $product")
            textViewProductName.text = product.name
            textViewProductCategory.text = product.category
            textViewProductRating.text = product.rating.toString()
            textViewProductPrice.text = "${product.price}$"

            Glide.with(itemView)
                .load(product.image)
                .centerCrop()
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.product_image_radius)))
                .error(R.drawable.ic_logo)
                .into(imageViewProduct)

            itemView.setOnClickListener {
                listener?.onClick(product)
            }
        }
    }
}