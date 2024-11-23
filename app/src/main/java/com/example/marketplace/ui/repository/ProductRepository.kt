package com.example.marketplace.ui.repository

import com.example.marketplace.ui.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getProducts(callback: (List<Product>) -> Unit) {
        db.collection("products").get()
            .addOnSuccessListener { result ->
                val products = result.map { it.toObject(Product::class.java) }
                callback(products)
            }
            .addOnFailureListener {
                // Manejar el error
            }
    }

    fun addProduct(product: Product, callback: (Boolean) -> Unit) {
        db.collection("products").add(product)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}