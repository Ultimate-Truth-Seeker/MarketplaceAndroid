package com.example.marketplace.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketplace.ui.model.Product

class ProductDetailViewModel : ViewModel() {
    var selectedProduct: Product? = null
        private set

    fun setSelectedProduct(product: Product) {
        selectedProduct = product
    }
}
