// En com.example.marketplace.ui.viewmodel.ShoppingCartViewModel.kt
package com.example.marketplace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoppingCartViewModel(private val userId: String) : ViewModel() {
    private val userRepository = UserRepository()

    private val _cartItems = MutableStateFlow<List<CartItemWithProduct>>(emptyList())
    val cartItems: StateFlow<List<CartItemWithProduct>> = _cartItems.asStateFlow()

    init {
        // Load the cart items when the ViewModel is initialized
        loadCartItems()
    }

    private fun loadCartItems() {
        userRepository.getUserCartWithProducts(userId) { cartItemsWithProducts ->
            _cartItems.value = cartItemsWithProducts
        }
    }

    fun addProductToCart(product: Product) {
        userRepository.addItemToCart(userId, CartItem(product.id, 1)) { success ->
            if (success) {
                // Reload the cart items after adding a product
                loadCartItems()
            } else {
                // Handle error
            }
        }
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



class ShoppingCartViewModelFactory(private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            return ShoppingCartViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
