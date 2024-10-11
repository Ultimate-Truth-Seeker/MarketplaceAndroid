package com.example.marketplace.ui.view
data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int // Recurso de imagen
)

data class CartItem(
    val product: Product,
    var quantity: Int
)