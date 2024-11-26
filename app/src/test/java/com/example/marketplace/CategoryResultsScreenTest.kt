package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.ui.model.Product
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.marketplace.ui.view.CategoryResultsScreen


@RunWith(AndroidJUnit4::class)
class CategoryResultsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysCategoryName() {
        val categoryName = "Electronics"
        val products = emptyList<Product>()

        composeTestRule.setContent {
            CategoryResultsScreen(
                categoryName = categoryName,
                products = products,
                onProductClick = {},
                goBack = {}
            )
        }

        composeTestRule.onNodeWithText(categoryName).assertIsDisplayed()
    }

    @Test
    fun displaysProductsInGrid() {
        val products = listOf(
            Product(id = "1", name = "Laptop", description = "", price = 999.99, category = "",  imageUrl = ""),
            Product(id = "2", name = "Smartphone", description = "", price = 799.99, category = "", imageUrl = "")
        )

        composeTestRule.setContent {
            CategoryResultsScreen(
                categoryName = "Electronics",
                products = products,
                onProductClick = {},
                goBack = {}
            )
        }

        composeTestRule.onNodeWithText("Laptop").assertIsDisplayed()
        composeTestRule.onNodeWithText("Smartphone").assertIsDisplayed()
    }

    @Test
    fun clickingProductCallsOnProductClick() {
        var clickedProduct: Product? = null
        val products = listOf(
            Product(id = "1", name = "Laptop", description = "", price = 999.99, category = "", imageUrl = "")
        )

        composeTestRule.setContent {
            CategoryResultsScreen(
                categoryName = "Electronics",
                products = products,
                onProductClick = { clickedProduct = it },
                goBack = {}
            )
        }

        composeTestRule.onNodeWithText("Laptop").performClick()

        assert(clickedProduct != null)
        assert(clickedProduct?.name == "Laptop")
    }

    @Test
    fun clickingBackCallsGoBack() {
        var goBackCalled = false

        composeTestRule.setContent {
            CategoryResultsScreen(
                categoryName = "Electronics",
                products = emptyList(),
                onProductClick = {},
                goBack = { goBackCalled = true }
            )
        }

        // Asumimos que el Icono de retroceso tiene un contentDescription
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(goBackCalled)
    }
}
