package com.qlmat.android.smartsupplier.ui.home

import com.qlmat.android.smartsupplier.data.model.Product

interface OnProductClickListener {
    fun onClick(product: Product)
}