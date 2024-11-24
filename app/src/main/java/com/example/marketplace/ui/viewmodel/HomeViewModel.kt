package com.example.marketplace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.database.AppDatabase
import com.example.marketplace.ui.model.Product
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
        viewModelScope.launch {
            _searchResults.value = AppDatabase.sampleProducts
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _searchResults.value = AppDatabase.searchProducts(query)
        }
    }
}
