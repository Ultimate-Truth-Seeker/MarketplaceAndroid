package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.ui.model.Product
import com.example.marketplace.ui.viewmodel.HomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.marketplace.ui.view.HomeScreen

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysSearchBar() {
        composeTestRule.setContent {
            HomeScreen(
                onCategoryClick = {},
                onCartClick = {},
                onProfileClick = {},
                onProductClick = {}
            )
        }

        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
    }

    @Test
    fun displaysCategories() {
        composeTestRule.setContent {
            HomeScreen(
                onCategoryClick = {},
                onCartClick = {},
                onProfileClick = {},
                onProductClick = {}
            )
        }

        composeTestRule.onNodeWithText("Clothing").assertIsDisplayed()
        composeTestRule.onNodeWithText("Food").assertIsDisplayed()
        composeTestRule.onNodeWithText("Technology").assertIsDisplayed()
    }

    @Test
    fun clickingCategoryCallsOnCategoryClick() {
        var clickedCategory: String? = null

        composeTestRule.setContent {
            HomeScreen(
                onCategoryClick = { clickedCategory = it },
                onCartClick = {},
                onProfileClick = {},
                onProductClick = {}
            )
        }

        composeTestRule.onNodeWithText("Clothing").performClick()

        assert(clickedCategory == "Clothing")
    }

    @Test
    fun displaysProducts() {
        val homeViewModel = HomeViewModel()
        val sampleProducts = listOf(
            Product(id = "1", name = "Product 1", description = "", price = 10.0, category = "",  imageUrl = ""),
            Product(id = "2", name = "Product 2", description = "", price = 20.0, category = "",  imageUrl = "")
        )
        homeViewModel.updateProducts(sampleProducts)

        composeTestRule.setContent {
            HomeScreen(
                onCategoryClick = {},
                onCartClick = {},
                onProfileClick = {},
                onProductClick = {},
                homeViewModel = homeViewModel
            )
        }

        composeTestRule.onNodeWithText("Product Product 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Product Product 2").assertIsDisplayed()
    }
}
