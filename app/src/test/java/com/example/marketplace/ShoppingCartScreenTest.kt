package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.viewmodel.ShoppingCartViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.marketplace.ui.view.ShoppingCartScreen

@RunWith(AndroidJUnit4::class)
class ShoppingCartScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysCartItems() {
        val shoppingCartViewModel = ShoppingCartViewModel()
        val product = Product(id = "1", name = "Laptop", description = "", price = 999.99, category = "",  imageUrl = "")
        shoppingCartViewModel.addProductToCart(product)

        composeTestRule.setContent {
            ShoppingCartScreen(
                onHomeClick = {},
                onProfileClick = {},
                onCheckout = {},
                shoppingCartViewModel = shoppingCartViewModel
            )
        }

        composeTestRule.onNodeWithText("Laptop").assertIsDisplayed()
    }

    @Test
    fun clickingRemoveRemovesItemFromCart() {
        val shoppingCartViewModel = ShoppingCartViewModel()
        val product = Product(id = "1", name = "Laptop", description = "", price = 999.99, category = "",  imageUrl = "")
        shoppingCartViewModel.addProductToCart(product)

        composeTestRule.setContent {
            ShoppingCartScreen(
                onHomeClick = {},
                onProfileClick = {},
                onCheckout = {},
                shoppingCartViewModel = shoppingCartViewModel
            )
        }

        composeTestRule.onNodeWithText("Remove").performClick()

        assert(shoppingCartViewModel.cartItems.value.isEmpty())
    }

    @Test
    fun displaysTotalPrice() {
        val shoppingCartViewModel = ShoppingCartViewModel()
        val product1 = Product(id = "1", name = "Laptop", description = "", price = 1000.0, category = "",  imageUrl = "")
        val product2 = Product(id = "2", name = "Mouse", description = "", price = 50.0, category = "",  imageUrl = "")
        shoppingCartViewModel.addProductToCart(product1)
        shoppingCartViewModel.addProductToCart(product2)

        composeTestRule.setContent {
            ShoppingCartScreen(
                onHomeClick = {},
                onProfileClick = {},
                onCheckout = {},
                shoppingCartViewModel = shoppingCartViewModel
            )
        }

        composeTestRule.onNodeWithText("Total: $1050.0").assertIsDisplayed()
    }
}
