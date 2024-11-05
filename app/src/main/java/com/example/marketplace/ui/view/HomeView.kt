package com.example.marketplace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marketplace.database.AppDatabase
import com.example.marketplace.navigation.BottomNavigationMenu
import com.example.marketplace.ui.model.Product

@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    var searchResults by remember { mutableStateOf<List<Product>>(ArrayList<Product>()) }
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F8F8))
        .padding(16.dp)) {
        // Barra de búsqueda
        SearchBar(onSearch = {query -> searchResults = AppDatabase.searchProducts(query)})

        // Sección de categorías destacadas
        CategoriesSection(onCategoryClick = onCategoryClick)

        // Listado de productos populares
        ProductsList(searchResults, onProductClick)
        Spacer(modifier = Modifier.weight(1f))

        // Menú inferior
        BottomNavigationMenu(
            onHomeClick = {},
            onCartClick = onCartClick,
            onProfileClick = onProfileClick
        )
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        trailingIcon = {
            IconButton(onClick = { onSearch(searchQuery) }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}

@Composable
fun CategoriesSection(onCategoryClick: (String) -> Unit) {
    val categories = listOf("Clothing", "Food", "Technology")

    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {
        items(categories) { category ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onCategoryClick(category) },
                //elevation = 4.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(Icons.Filled.Star, contentDescription = category)
                    Text(text = category)
                }
            }
        }
    }
}

@Composable
fun ProductsList(products: List<Product>, onProductClick: (Product) -> Unit) {
    // Aquí deberías implementar el listado de productos, usando LazyColumn o LazyVerticalGrid.
    // Este es un ejemplo básico.
    LazyColumn {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {  },
              //  elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).clickable { onProductClick(product) }

                ) {
                    Text(text = "Product ${product.name}")
                    Text(text = "$${product.price}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

