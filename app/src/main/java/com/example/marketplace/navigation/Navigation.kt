package com.example.marketplace.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marketplace.R
import com.example.marketplace.database.AppDatabase
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.view.CategoryResultsScreen
import com.example.marketplace.ui.view.CheckoutScreen
import com.example.marketplace.ui.view.HomeScreen
import com.example.marketplace.ui.view.LoginScreen
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.model.User
import com.example.marketplace.ui.view.ProductDetailScreen
import com.example.marketplace.ui.view.ShoppingCartScreen
import com.example.marketplace.ui.view.UserProfileScreen
import com.example.marketplace.ui.view.UserRegistrationScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var sampleProducts = AppDatabase.sampleProducts
    var sampleUsers = AppDatabase.sampleUsers
    var sampleUser by remember { mutableStateOf<User?>(null) }
    var sampleCartItems by remember { mutableStateOf(sampleUser?.cart) }

    NavHost(navController = navController,
        startDestination = "/",
        modifier = modifier) {
        composable(route = "/") {
            LoginScreen(
                navController = navController,
                onLoginClick = { email, password ->
                    println(AppDatabase.findUser(email, password).toString())
                    if (AppDatabase.findUser(email, password) != null) {
                        sampleUser = AppDatabase.findUser(email, password)
                        navController.navigate("Home")
                    }
                },
                onRegisterClick = {
                    navController.navigate("SignUp")
                }
            )
        }
        composable(route = "SignUp") {
            UserRegistrationScreen(
                onLoginClick = { navController.navigate("/")},
                onRegister = { name, email, password ->
                    val user = User(name, email, password, mutableListOf())
                    sampleUsers.add(user)
                    sampleUser = user
                    sampleCartItems = user.cart
                    navController.navigate("Home")
                }
            )
        }
        composable(route = "Home") {
            HomeScreen(
              onCategoryClick = { category ->
                  navController.navigate("Category/$category")
              },
                onCartClick = {
                    navController.navigate("Cart")
                    selectedTab = 1
                              },
                onProfileClick = {
                    navController.navigate("Profile")
                    selectedTab = 2
                                 },
                onProductClick = {product -> navController.navigate("Product/${product.name}")}
            )
        }
        composable(
            route = "Category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })

        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            CategoryResultsScreen(categoryName?:"", sampleProducts.filter { it.category == categoryName },  {product -> navController.navigate("Product/${product.name}")},
                {navController.navigate("Home")
                selectedTab = 0},)
        }
        composable(
            route = "Product/{product}",
            arguments = listOf(navArgument("product") {type = NavType.StringType})
        ) { backStackEntry ->

            val product = backStackEntry.arguments?.getString("product")
            AppDatabase.sampleProducts.find { it.name == product }
                ?.let { ProductDetailScreen(product = it,
                    onCartClick = {
                        sampleUser = AppDatabase.addCartItem(sampleUser!!, it)
                        navController.navigate("Cart")
                                  selectedTab = 1},
                    onBackClick = {navController.navigate("Home")
                    selectedTab = 0}) }
        }
        composable(route = "Cart") {
            ShoppingCartScreen(
                products = sampleUser?.cart?: emptyList(),
                onProfileClick = {navController.navigate("Profile")
                                 selectedTab = 2},
                onHomeClick = {navController.navigate("Home")
                              selectedTab = 0},
                onCheckout = {
                    if (sampleUser?.cart?.isEmpty() != true) {
                    navController.navigate("Checkout")}},
                onUpdateQuantity = {item, n ->
                    if (n > 0) {
                        sampleUser?.cart?.get(sampleUser?.cart!!.indexOf(item))?.quantity = n
                    }
                },
                onRemoveProduct = {item ->
                    sampleUser?.cart?.remove(item)
                }
            )
        }
        composable(route = "Checkout") {
            CheckoutScreen(
                products = sampleUser?.cart?: emptyList(),
                totalPrice = sampleUser?.cart?.sumOf { it.product.price }?: 0.0,
                onConfirmPurchase = {
                    sampleUser?.cart?.removeIf { true }
                    navController.navigate("Home")
                selectedTab = 0}
            )
        }
        composable(route = "Profile") {
            UserProfileScreen(
                userName = sampleUser?.username?:"",
                userEmail = sampleUser?.email?:"",
                userProfilePic = R.drawable.perfil_1,
                onEditProfile = {},
                onChangePassword = {},
                onManageAddresses = {},
                onHomeClick = {navController.navigate("Home")
                              selectedTab = 0},
                onCartClick = { navController.navigate("Cart")
                              selectedTab = 1},
                onLogout = {navController.navigate("/")
                sampleUser = null
                selectedTab = 0}
            )
        }
    }
}

