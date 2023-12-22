package com.qlmat.android.smartsupplier.ui.history

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.data.model.Order
import com.qlmat.android.smartsupplier.databinding.ItemOrderBinding

class OrdersAdapter(
    private val resources: Resources
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    private var orders = listOf<Order>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersAdapter.OrdersViewHolder {
        val itemBinding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrdersViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(
        holder: OrdersViewHolder,
        position: Int
    ) {
        val order: Order = orders[position]
        holder.bind(order)
    }

    fun setData(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    inner class OrdersViewHolder(
        private val itemOrderBinding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(itemOrderBinding.root) {
        fun bind(order: Order) = with(itemOrderBinding) {
            textViewOrderProductName.text = order.productName
            textViewOrderProductQuantity.text = order.quantity.toString()
            textViewOrderProductDeliveryMethod.text = order.deliveryOption
            textViewOrderProductDeliveryDetails.text = order.deliveryDetails

            Glide.with(itemView)
                .load(order.productImage)
                .centerCrop()
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.product_image_radius)))
                .error(R.drawable.ic_logo)
                .into(imageViewOrderProduct)
        }
    }
}