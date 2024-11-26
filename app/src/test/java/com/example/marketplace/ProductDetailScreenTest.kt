package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.viewmodel.ShoppingCartViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.marketplace.ui.view.ProductDetailScreen

@RunWith(AndroidJUnit4::class)
class ProductDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysProductDetails() {
        val product = Product(
            id = "1",
            name = "Laptop",
            description = "High-performance laptop",
            price = 999.99,
            category = "",
            imageUrl = ""
        )

        composeTestRule.setContent {
            ProductDetailScreen(
                product = product,
                onCartClick = {},
                onBackClick = {}
            )
        }

        composeTestRule.onNodeWithText("Laptop").assertIsDisplayed()
        composeTestRule.onNodeWithText("High-performance laptop").assertIsDisplayed()
        composeTestRule.onNodeWithText("$999.99").assertIsDisplayed()
    }

    @Test
    fun clickingAddToCartAddsProductToCart() {
        val product = Product(
            id = "1",
            name = "Laptop",
            description = "",
            price = 999.99,
            category = "",
            imageUrl = ""
        )
        val shoppingCartViewModel = ShoppingCartViewModel()

        composeTestRule.setContent {
            ProductDetailScreen(
                product = product,
                onCartClick = {},
                onBackClick = {},
                shoppingCartViewModel = shoppingCartViewModel
            )
        }

        composeTestRule.onNodeWithText("Añadir al Carrito").performClick()

        val cartItems = shoppingCartViewModel.cartItems.value
        assert(cartItems.isNotEmpty())
        assert(cartItems.first().product.id == "1")
    }
}
