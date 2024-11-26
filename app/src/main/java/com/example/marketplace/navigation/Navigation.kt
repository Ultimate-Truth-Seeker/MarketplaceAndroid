package com.example.marketplace.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.marketplace.ui.viewmodel.ShoppingCartViewModel
import com.example.marketplace.ui.viewmodel.ShoppingCartViewModelFactory

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var sampleProducts = ProductRepository()
    var sampleUsers = UserRepository()
    val userViewModel: UserViewModel = viewModel()

    val sampleUser by userViewModel.sampleUser.observeAsState()

    var sampleCartItems = userViewModel.sampleUser.observeAsState().value?.cart
    val shoppingCartViewModel: ShoppingCartViewModel? = sampleUser?.uid?.let { userId ->
        viewModel(factory = ShoppingCartViewModelFactory(userId))
    }

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
                        userViewModel.signIn(email, password)
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
                onLoginClick = { navController.navigate("/") },
                onRegister = { name, email, password ->
                    //val user = User(name, email, password, mutableListOf())
                    sampleUsers.createUser(
                        email, password, name
                    )
                    userViewModel.signIn(email, password)
                    navController.navigate("Home")

                }
            )
            LaunchedEffect(sampleUser) {
                if (sampleUser != null) {
                    navController.navigate("Home")
                    sampleCartItems = sampleUser?.cart
                }
            }
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
                route = "Category/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })

            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                val productsState = remember { mutableStateOf<List<Product>>(emptyList()) }

                // Fetch products asynchronously
                LaunchedEffect(category) {
                    sampleProducts.getProductsByCategory(category!!) { products ->
                        productsState.value = products
                    }
                }
                CategoryResultsScreen(
                    category ?: "",
                    productsState.value,
                    { product -> navController.navigate("Product/${product.id}") },
                    {
                        navController.navigate("Home")
                        selectedTab = 0
                    },
                )
            }
            composable(
                route = "Product/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                val productState = remember { mutableStateOf<Product?>(null) }
                // Fetch product asynchronously
                LaunchedEffect(id) {
                    sampleProducts.getProductById(id!!) { product ->
                        productState.value = product
                    }
                }

                // Show a loading indicator while the product is null
                if (productState.value == null || shoppingCartViewModel == null) {
                    CircularProgressIndicator()
                } else {
                    ProductDetailScreen(
                        product = productState.value!!,
                        onCartClick = {
                            if (shoppingCartViewModel != null) {
                                shoppingCartViewModel.addProductToCart(productState.value!!)
                                navController.navigate("Cart")
                                selectedTab = 1
                            } else {
                                navController.navigate("/")
                            }
                        },
                        onBackClick = {
                            navController.navigate("Home")
                            selectedTab = 0
                        },
                        shoppingCartViewModel = shoppingCartViewModel!!
                    )
                }
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
                        if (shoppingCartViewModel != null) {
                            if (shoppingCartViewModel.cartItems.value.size > 0) {
                                navController.navigate("Checkout")
                            }
                        }
                    },
                    shoppingCartViewModel = shoppingCartViewModel!!

                    )
            }
            composable(route = "Checkout") {
                var cartwithproducts = shoppingCartViewModel?.cartItems?.value

                if (cartwithproducts != null) {
                    CheckoutScreen(
                        products = cartwithproducts,
                        totalPrice = cartwithproducts.sumOf { it.quantity * it.product.price },
                        onConfirmPurchase = {
                            for (item in cartwithproducts) {
                                shoppingCartViewModel?.removeProductFromCart(item)
                            }
                            navController.navigate("Home")
                            selectedTab = 0
                        }
                    )
                }
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
                        userViewModel.signOut()
                        selectedTab = 0
                    }
                )
            }
        }
}



