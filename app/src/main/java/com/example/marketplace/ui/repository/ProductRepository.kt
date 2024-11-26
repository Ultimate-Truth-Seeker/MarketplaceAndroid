package com.example.marketplace.ui.repository
import com.example.marketplace.database.AppDatabase
import com.example.marketplace.ui.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {

    private val db = AppDatabase().db
    private val productsCollection = AppDatabase().productsCollection

    fun addProduct(product: Product, onComplete: (Boolean) -> Unit) {
        productsCollection.document(product.id)
            .set(product)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getProductById(id: String, onComplete: (Product?) -> Unit): Product? {
        var product:Product? = null
        productsCollection.document(id)
            .get()
            .addOnSuccessListener { document ->
                product = document.toObject(Product::class.java)
                onComplete(product)
            }
            .addOnFailureListener { onComplete(null) }
        return product
    }

    fun getAllProducts(onComplete: (List<Product>) -> Unit) {
        productsCollection.get()
            .addOnSuccessListener { result ->
                val products = result.mapNotNull { it.toObject(Product::class.java) }
                onComplete(products)
            }
            .addOnFailureListener { onComplete(emptyList()) }
    }

    fun updateProduct(product: Product, onComplete: (Boolean) -> Unit) {
        productsCollection.document(product.id)
            .set(product)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteProduct(id: String, onComplete: (Boolean) -> Unit) {
        productsCollection.document(id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getProductsByCategory(category: String, onComplete: (List<Product>) -> Unit): List<Product> {
        var products = emptyList<Product>()
        productsCollection.whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { result ->
                products = result.mapNotNull { it.toObject(Product::class.java) }
                onComplete(products)
            }
            .addOnFailureListener {
                onComplete(emptyList())
            }
        return products
    }
}