// En com.example.marketplace.ui.viewmodel.ShoppingCartViewModel.kt
package com.example.marketplace.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShoppingCartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemWithProduct>>(emptyList())
    val cartItems: StateFlow<List<CartItemWithProduct>> = _cartItems

    fun addProductToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            currentItems[currentItems.indexOf(existingItem)] = updatedItem
        } else {
            currentItems.add(CartItemWithProduct(product, 1))
        }
        _cartItems.value = currentItems
    }

    fun removeProductFromCart(cartItem: CartItemWithProduct) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.remove(cartItem)
        _cartItems.value = currentItems
    }

    fun updateProductQuantity(cartItem: CartItemWithProduct, quantity: Int) {
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == cartItem.product.id }
        if (index != -1) {
            if (quantity > 0) {
                currentItems[index] = cartItem.copy(quantity = quantity)
            } else {
                currentItems.removeAt(index)
            }
            _cartItems.value = currentItems
        }
    }

    fun calculateTotal(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }
}
