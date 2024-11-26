package com.example.marketplace.navigation

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
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.view.CategoryResultsScreen
import com.example.marketplace.ui.view.CheckoutScreen
import com.example.marketplace.ui.view.HomeScreen
import com.example.marketplace.ui.view.LoginScreen
import com.example.marketplace.ui.model.User
import com.example.marketplace.ui.repository.ProductRepository
import com.example.marketplace.ui.repository.UserRepository
import com.example.marketplace.ui.view.ProductDetailScreen
import com.example.marketplace.ui.view.ShoppingCartScreen
import com.example.marketplace.ui.view.UserProfileScreen
import com.example.marketplace.ui.view.UserRegistrationScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var sampleProducts = ProductRepository()
    var sampleUsers = UserRepository()
    var sampleUser by remember { mutableStateOf<User?>(null) }
    var sampleCartItems by remember { mutableStateOf(sampleUser?.cart) }

    NavHost(
        navController = navController,
        startDestination = "/",
        modifier = modifier
    ) {
        composable(route = "/") {
            LoginScreen(
                navController = navController,
                onLoginClick = { email, password ->
                    if (email != "" && password != "") {
                        sampleUsers.signInUser(email, password) { user ->
                            if (user != null) {
                                sampleUsers.getUser(user.uid) { u ->
                                    sampleUser = u
                                }
                                navController.navigate("Home")
                            }
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate("SignUp")
                }
            )
        }
        composable(route = "SignUp") {
            UserRegistrationScreen(
                onLoginClick = { navController.navigate("/") },
                onRegister = { name, email, password ->
                    val user = User(name, email, password, mutableListOf())
                    sampleUsers.createUser(
                        email, password, name
                    )
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
                    onProductClick = { product -> navController.navigate("Product/${product.id}") }
                )
            }
            composable(
                route = "Category/{categoryName}",
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })

            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName")
                var products = emptyList<Product>()
                sampleProducts.getProductsByCategory(categoryName!!) { list ->
                    products = list
                }
                CategoryResultsScreen(
                    categoryName ?: "",
                    products,
                    { product -> navController.navigate("Product/${product.id}") },
                    {
                        navController.navigate("Home")
                        selectedTab = 0
                    },
                )
            }
            composable(
                route = "Product/{product}",
                arguments = listOf(navArgument("product") { type = NavType.StringType })
            ) { backStackEntry ->

                val productid = backStackEntry.arguments?.getString("product")
                var product = Product()
                sampleProducts.getProductById(productid!!) { pro ->
                    if (pro != null) {
                        product = pro
                    }
                }
                ProductDetailScreen(product = product,
                    onCartClick = {
                        sampleUsers.addItemToCart(sampleUser!!.uid, CartItem(productid, 1)) {}
                        navController.navigate("Cart")
                        selectedTab = 1
                    },
                    onBackClick = {
                        navController.navigate("Home")
                        selectedTab = 0
                    })
            }
            composable(route = "Cart") {
                ShoppingCartScreen(
                    onProfileClick = {
                        navController.navigate("Profile")
                        selectedTab = 2
                    },
                    onHomeClick = {
                        navController.navigate("Home")
                        selectedTab = 0
                    },
                    onCheckout = {
                        if (sampleUser?.cart?.isEmpty() != true) {
                            navController.navigate("Checkout")
                        }
                    },

                    )
            }
            composable(route = "Checkout") {
                var cartwithproducts = emptyList<CartItemWithProduct>()
                sampleUsers.getUserCartWithProducts(sampleUser!!.uid) {
                    cartwithproducts = it
                }

                CheckoutScreen(
                    products = cartwithproducts,
                    totalPrice = cartwithproducts.sumOf { it.quantity * it.product.price },
                    onConfirmPurchase = {
                        sampleUsers.updateUserCart(sampleUser!!.uid, emptyList(), {})
                        navController.navigate("Home")
                        selectedTab = 0
                    }
                )
            }
            composable(route = "Profile") {
                UserProfileScreen(
                    userName = sampleUser?.username ?: "",
                    userEmail = sampleUser?.email ?: "",
                    userProfilePic = R.drawable.perfil_1,
                    onEditProfile = {},
                    onChangePassword = {},
                    onManageAddresses = {},
                    onHomeClick = {
                        navController.navigate("Home")
                        selectedTab = 0
                    },
                    onCartClick = {
                        navController.navigate("Cart")
                        selectedTab = 1
                    },
                    onLogout = {
                        navController.navigate("/")
                        sampleUser = null
                        selectedTab = 0
                    }
                )
            }
        }
}


