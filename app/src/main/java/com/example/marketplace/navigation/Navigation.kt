package com.example.marketplace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marketplace.R
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.view.CategoryResultsScreen
import com.example.marketplace.ui.view.CheckoutScreen
import com.example.marketplace.ui.view.HomeScreen
import com.example.marketplace.ui.view.LoginScreen
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.view.ProductDetailScreen
import com.example.marketplace.ui.view.ShoppingCartScreen
import com.example.marketplace.ui.view.UserProfileScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val sampleProducts = listOf(
        Product("Producto 1", "Descripción del producto", 25.99, R.drawable.sample_image1),
        Product("Producto 2", "Descripción del producto", 30.50, R.drawable.sample_image2),
        Product("Producto 3", "Descripción del producto", 15.00, R.drawable.sample_image3),
        // Agrega más productos de ejemplo
    )
    val sampleCartItems = listOf(
        CartItem(Product("Producto 1", "Descripción del producto", 25.99, R.drawable.sample_image1), 1),
        CartItem(Product("Producto 2", "Descripción del producto", 30.50, R.drawable.sample_image2), 2),
        CartItem(Product("Producto 3", "Descripción del producto", 15.00, R.drawable.sample_image3), 1)
    )
    NavHost(navController = navController,
        startDestination = "/",
        modifier = modifier) {
        composable(route = "/") {
            LoginScreen(
                navController = navController,
                onLoginClick = {
                    navController.navigate("Home")
                },
                onRegisterClick = {}
            )
        }
        composable(route = "Home") {
            HomeScreen(
              onCategoryClick = { category ->
                  navController.navigate("Category/$category")
              },
                onCartClick = { navController.navigate("Cart") },
                onProfileClick = {navController.navigate("Profile")},
                onSearch = {search -> navController.navigate("Product")}
            )
        }
        composable(
            route = "Category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })

        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            CategoryResultsScreen(categoryName?:"", sampleProducts, {navController.navigate("Home")})
        }
        composable(route = "Product") {
            val sampleProduct = Product(
                name = "Producto de Ejemplo",
                description = "Este es un producto de ejemplo con una descripción detallada.",
                price = 3399.99,
                imageRes = R.drawable.sample_image // Cambia esto por una imagen en drawable
            )
            ProductDetailScreen(product = sampleProduct, onCartClick = {navController.navigate("Cart")}, onBackClick = {navController.navigate("Home")})
        }
        composable(route = "Cart") {
            ShoppingCartScreen(
                products = sampleCartItems,
                onProfileClick = {navController.navigate("Profile")},
                onHomeClick = {navController.navigate("Home")},
                onCheckout = {navController.navigate("Checkout")},
                onUpdateQuantity = {_, _ ->},
                onRemoveProduct = {}
            )
        }
        composable(route = "Checkout") {
            CheckoutScreen(
                products = sampleCartItems,
                totalPrice = 101.99,
                onConfirmPurchase = {navController.navigate("Home")}
            )
        }
        composable(route = "Profile") {
            UserProfileScreen(
                userName = "John Doe",
                userEmail = "john.doe@example.com",
                userProfilePic = R.drawable.perfil_1,
                onEditProfile = {},
                onChangePassword = {},
                onManageAddresses = {},
                onHomeClick = {navController.navigate("Home")},
                onCartClick = { navController.navigate("Cart") },
                onLogout = {navController.navigate("/")}
            )
        }
    }
}

