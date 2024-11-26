package com.example.marketplace.ui.model
data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val category: String = "",
    val imageUrl: String = "" // URL de la imagen
)

data class CartItem(
    val productId: String = "",
    var quantity: Int = 0
)

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val cart: List<CartItem> = emptyList()
)

data class CartItemWithProduct(
    val product: Product,
    val quantity: Int
)