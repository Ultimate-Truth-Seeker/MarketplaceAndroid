package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.marketplace.ui.view.UserProfileScreen

@RunWith(AndroidJUnit4::class)
class UserProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysUserInfo() {
        composeTestRule.setContent {
            UserProfileScreen(
                userName = "Test User",
                userEmail = "test@example.com",
                userProfilePic = R.drawable.perfil_1,
                onEditProfile = {},
                onChangePassword = {},
                onManageAddresses = {},
                onHomeClick = {},
                onCartClick = {},
                onLogout = {}
            )
        }

        composeTestRule.onNodeWithText("Test User").assertIsDisplayed()
        composeTestRule.onNodeWithText("test@example.com").assertIsDisplayed()
    }

    @Test
    fun clickingEditProfileCallsOnEditProfile() {
        var editProfileCalled = false

        composeTestRule.setContent {
            UserProfileScreen(
                userName = "",
                userEmail = "",
                userProfilePic = R.drawable.perfil_1,
                onEditProfile = { editProfileCalled = true },
                onChangePassword = {},
                onManageAddresses = {},
                onHomeClick = {},
                onCartClick = {},
                onLogout = {}
            )
        }

        composeTestRule.onNodeWithText("Edit Profile").performClick()

        assert(editProfileCalled)
    }

    @Test
    fun clickingLogoutCallsOnLogout() {
        var logoutCalled = false

        composeTestRule.setContent {
            UserProfileScreen(
                userName = "",
                userEmail = "",
                userProfilePic = R.drawable.perfil_1,
                onEditProfile = {},
                onChangePassword = {},
                onManageAddresses = {},
                onHomeClick = {},
                onCartClick = {},
                onLogout = { logoutCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Log Out").performClick()

        assert(logoutCalled)
    }
}
