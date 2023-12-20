package com.qlmat.android.smartsupplier.ui.order

import com.qlmat.android.smartsupplier.data.model.Warehouse

interface OnWarehouseClickListener {
    fun onClick(warehouse: Warehouse)
}