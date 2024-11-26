package com.example.marketplace.database

import com.example.marketplace.R
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.model.User
import com.google.firebase.firestore.FirebaseFirestore

class AppDatabase {
    val db = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("users")
    val productsCollection = db.collection("products")

}