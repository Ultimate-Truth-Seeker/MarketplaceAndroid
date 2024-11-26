package com.example.marketplace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.database.AppDatabase
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults

    init {
        // Cargar productos iniciales
        loadInitialProducts()
    }

    private fun loadInitialProducts() {
        ProductRepository().getAllProducts {
            viewModelScope.launch {
                _searchResults.value = it
            }
        }
    }

    fun searchProducts(query: String) {
        ProductRepository().getAllProducts {
            viewModelScope.launch {
                _searchResults.value = it.filter { it.name.contains(query) }
            }
        }
    }
    fun updateProducts(products: List<Product>) {
        _searchResults.value = products
    }
}
