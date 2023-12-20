package com.qlmat.android.smartsupplier.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qlmat.android.smartsupplier.data.model.Warehouse
import com.qlmat.android.smartsupplier.databinding.ItemWarehouseBinding

class WarehousesAdapter : RecyclerView.Adapter<WarehousesAdapter.WarehousesViewHolder>() {

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

            itemView.setOnClickListener {
                listener?.onClick(warehouse)
            }
        }
    }

}