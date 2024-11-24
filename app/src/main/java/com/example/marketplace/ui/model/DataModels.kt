package com.example.marketplace.ui.model
data class Product(
    val name: String,
    val id: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageRes: Int // Recurso de imagen
)

data class CartItem(
    val product: Product,
    var quantity: Int
)

data class User(
    val username: String,
    val email: String,
    val password: String,
    val cart: MutableList<CartItem>
)