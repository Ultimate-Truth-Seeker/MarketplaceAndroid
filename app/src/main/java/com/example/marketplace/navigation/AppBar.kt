package com.example.marketplace.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationMenu(
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = selectedTab == 0,
                onClick = onHomeClick
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
                label = { Text("Cart") },
                selected = selectedTab == 1,
                onClick = onCartClick
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = selectedTab == 2,
                onClick = onProfileClick
            )
        }
}