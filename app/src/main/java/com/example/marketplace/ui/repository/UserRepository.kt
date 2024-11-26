package com.example.marketplace.ui.repository

import com.example.marketplace.database.AppDatabase
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.model.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.util.nextAlphanumericString
import kotlin.random.Random

class UserRepository {
    private val db = AppDatabase().db
    private val usersCollection = AppDatabase().usersCollection
    private val productsCollection = AppDatabase().productsCollection

    fun createUser(email: String, password: String, username: String) {
        val uid = Random.nextAlphanumericString(10)
        val user = User(uid, username, email, password, emptyList())
        usersCollection.document(uid)
            .set(user)
            .addOnSuccessListener { println("Usuario creado exitosamente.") }
            .addOnFailureListener { println("Error al crear el usuario.") }

    }

    fun getAllUsers(onComplete: (List<User>) -> Unit) {
        usersCollection.get()
            .addOnSuccessListener { result ->
                val users = result.mapNotNull { it.toObject(User::class.java) }
                onComplete(users)
            }
            .addOnFailureListener { onComplete(emptyList()) }
    }

    fun signInUser(email: String, password: String, onComplete: (User?) -> Unit) {
        getAllUsers { users ->
            val user = users.find { it.email == email && it.password == password }
            onComplete(user)
        }
    }

    fun getUser(uid: String, onComplete: (User?) -> Unit) {
        usersCollection.document(uid)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                onComplete(user)
            }
            .addOnFailureListener { onComplete(null) }
    }

    fun updateUserCart(uid: String, cartItems: List<CartItem>, onComplete: (Boolean) -> Unit) {
        usersCollection.document(uid)
            .update("cart", cartItems)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun addItemToCart(uid: String, cartItem: CartItem, onComplete: (Boolean) -> Unit) {
        getUser(uid) { user ->
            if (user != null) {
                val updatedCart = user.cart.toMutableList()
                val existingItemIndex = updatedCart.indexOfFirst { it.productId == cartItem.productId }
                if (existingItemIndex != -1) {
                    // Si el producto ya está en el carrito, actualiza la cantidad
                    val existingItem = updatedCart[existingItemIndex]
                    existingItem.quantity += cartItem.quantity
                    updatedCart[existingItemIndex] = existingItem
                } else {
                    // Si es un nuevo producto, añádelo al carrito
                    updatedCart.add(cartItem)
                }
                updateUserCart(uid, updatedCart) { success ->
                    onComplete(success)
                }
            } else {
                onComplete(false)
            }
        }
    }

    fun getUserCartWithProducts(uid: String, onComplete: (List<CartItemWithProduct>) -> Unit) {
        getUser(uid) { user ->
            if (user != null) {
                val cartItems = user.cart
                val productIds = cartItems.map { it.productId }

                // Si el carrito está vacío, retornar una lista vacía
                if (productIds.isEmpty()) {
                    onComplete(emptyList())
                    return@getUser
                }

                // Debido a que Firestore limita las consultas 'whereIn' a 10 elementos,
                // debemos dividir la lista de productIds en lotes si es necesario
                val batches = productIds.chunked(10)
                val tasks = batches.map { batchIds ->
                    productsCollection.whereIn("id", batchIds)
                        .get()
                }

                // Ejecutar todas las tareas y esperar a que completen
                Tasks.whenAllSuccess<QuerySnapshot>(tasks)
                    .addOnSuccessListener { results ->
                        val products = results.flatMap { it.documents }
                            .mapNotNull { it.toObject(Product::class.java) }

                        // Mapear products a un diccionario para acceso rápido
                        val productsMap = products.associateBy { it.id }

                        // Combinar los productos con los cartItems
                        val cartItemsWithProducts = cartItems.mapNotNull { cartItem ->
                            val product = productsMap[cartItem.productId]
                            if (product != null) {
                                CartItemWithProduct(product, cartItem.quantity)
                            } else {
                                null // Producto no encontrado, posiblemente eliminado
                            }
                        }

                        onComplete(cartItemsWithProducts)
                    }
                    .addOnFailureListener {
                        onComplete(emptyList())
                    }
            } else {
                onComplete(emptyList())
            }
        }
    }
}