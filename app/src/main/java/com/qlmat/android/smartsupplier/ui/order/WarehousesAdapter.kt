package com.qlmat.android.smartsupplier.ui.order

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.data.model.Warehouse
import com.qlmat.android.smartsupplier.databinding.ItemWarehouseBinding

class WarehousesAdapter(
    private val resources: Resources
) : RecyclerView.Adapter<WarehousesAdapter.WarehousesViewHolder>() {

    private var warehouses = listOf<Warehouse>()
    private var listener: OnWarehouseClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WarehousesAdapter.WarehousesViewHolder {
        val itemBinding = ItemWarehouseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WarehousesViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = warehouses.size

    override fun onBindViewHolder(
        holder: WarehousesViewHolder,
        position: Int
    ) {
       val warehouse = warehouses[position]
        holder.bind(warehouse, listener)
    }

    fun setData(warehouses: List<Warehouse>) {
        this.warehouses = warehouses
        notifyDataSetChanged()
    }

    fun setListener(listener: OnWarehouseClickListener) {
        this.listener = listener
    }

    inner class WarehousesViewHolder(
        private val itemViewBinding: ItemWarehouseBinding
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(warehouse: Warehouse, listener: OnWarehouseClickListener?) = with(itemViewBinding) {
            textViewWarehouseName.text = warehouse.name
            textViewWarehouseAddress.text = warehouse.address

            if (warehouse.isSelected) {
                itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_item_card_selected, itemView.context.theme)
            } else {
                itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_item_card, itemView.context.theme)
            }

            itemView.setOnClickListener {
                warehouses.forEach {
                    it.isSelected = false
                    if (it.id == warehouse.id) {
                        it.isSelected = true
                    }
                }
                notifyDataSetChanged()
                listener?.onClick(warehouse)
            }
        }
    }

}