package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.model.Product
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.marketplace.ui.view.CheckoutScreen

@RunWith(AndroidJUnit4::class)
class CheckoutScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysTotalPrice() {
        val products = listOf<CartItemWithProduct>()
        val totalPrice = 150.0

        composeTestRule.setContent {
            CheckoutScreen(
                products = products,
                totalPrice = totalPrice,
                onConfirmPurchase = {}
            )
        }

        composeTestRule.onNodeWithText("Total: $$totalPrice").assertIsDisplayed()
    }

    @Test
    fun displaysProductSummary() {
        val products = listOf(
            CartItemWithProduct(
                product = Product(id = "1", name = "Product 1", description = "", price = 50.0, category = "", imageUrl = ""),
                quantity = 2
            )
        )
        val totalPrice = 100.0

        composeTestRule.setContent {
            CheckoutScreen(
                products = products,
                totalPrice = totalPrice,
                onConfirmPurchase = {}
            )
        }

        composeTestRule.onNodeWithText("Product 1 x2 - $100.0").assertIsDisplayed()
    }

    @Test
    fun clickingConfirmPurchaseCallsOnConfirmPurchase() {
        var confirmPurchaseCalled = false

        composeTestRule.setContent {
            CheckoutScreen(
                products = emptyList(),
                totalPrice = 0.0,
                onConfirmPurchase = { confirmPurchaseCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Confirm Purchase").performClick()

        assert(confirmPurchaseCalled)
    }
}
