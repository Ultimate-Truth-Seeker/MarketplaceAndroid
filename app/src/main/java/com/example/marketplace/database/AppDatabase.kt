package com.example.marketplace.database

import com.example.marketplace.R
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.model.User

object AppDatabase {
    var sampleProducts = mutableListOf(

            Product("Producto 1", "Descripción del producto", 25.99, "Clothing", R.drawable.sample_image1),
            Product("Producto 2", "Descripción del producto", 30.50, "Food", R.drawable.sample_image2),
            Product("Producto 3", "Descripción del producto", 15.00, "Technology", R.drawable.sample_image3),
            Product(
                name = "Producto de Ejemplo",
        description = "Este es un producto de ejemplo con una descripción detallada.",
        price = 3399.99,
               category = "Clothing" ,
        imageRes = R.drawable.sample_image // Cambia esto por una imagen en drawable
        ),
            Product("Teléfono", "Un lindo telefono", 100.50, "Technology", R.drawable.sample_image),
            Product("Pizza", "Mama mía", 40.00, "Food", R.drawable.sample_image2),
            Product("Pantalones", "lindos pantalones", 100.00, "Clothing", R.drawable.sample_image3)
    )

    var sampleUsers = mutableListOf(
            User("user", "email1@example.com", "password", mutableListOf()),
            User("user1","email2@example.com", "password", mutableListOf()),
            User("user2","email3@example.com", "password", mutableListOf())
        )


    fun findUser(email: String, password: String): User? {
        return sampleUsers.find { (it.password == password && it.email==email) }
    }

    fun searchProducts(query: String): List<Product> {
        return sampleProducts.filter { it.name.contains(query) }
    }

    fun addCartItem(user: User, product: Product): User {
        var index = sampleUsers.indexOf(user)
        if (user.cart.find { it.product == product } == null) {
            user.cart.add(CartItem(product, 0))
        }
        user.cart.find { it.product == product }?.quantity = user.cart.find { it.product == product }?.quantity!! + 1
        sampleUsers[index] = user
        println(user.toString())
        return user
    }
}