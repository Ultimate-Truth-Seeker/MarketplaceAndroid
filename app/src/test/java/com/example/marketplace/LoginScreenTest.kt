package com.example.marketplace.ui.view

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marketplace.ui.view.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysLoginFields() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val navController = TestNavHostController(context)

        composeTestRule.setContent {
            LoginScreen(
                navController = navController,
                onLoginClick = { _, _ -> },
                onRegisterClick = {}
            )
        }

        composeTestRule.onNode(hasLabelText("Email")).assertIsDisplayed()
        composeTestRule.onNode(hasLabelText("Password")).assertIsDisplayed()
    }

    @Test
    fun clickingLoginCallsOnLoginClick() {
        var loginCalled = false
        var enteredEmail = ""
        var enteredPassword = ""

        val context = ApplicationProvider.getApplicationContext<Context>()
        val navController = TestNavHostController(context)

        composeTestRule.setContent {
            LoginScreen(
                navController = navController,
                onLoginClick = { email, password ->
                    loginCalled = true
                    enteredEmail = email
                    enteredPassword = password
                },
                onRegisterClick = {}
            )
        }

        composeTestRule.onNode(hasLabelText("Email")).performTextInput("test@example.com")
        composeTestRule.onNode(hasLabelText("Password")).performTextInput("password")
        composeTestRule.onNodeWithText("Sign Up").performClick()

        assert(loginCalled)
        assert(enteredEmail == "test@example.com")
        assert(enteredPassword == "password")
    }

    @Test
    fun clickingRegisterCallsOnRegisterClick() {
        var registerCalled = false

        val context = ApplicationProvider.getApplicationContext<Context>()
        val navController = mmTestNavHostController(context)

        composeTestRule.setContent {
            LoginScreen(
                navController = navController,
                onLoginClick = { _, _ -> },
                onRegisterClick = { registerCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Register").performClick()

        assert(registerCalled)
    }
}
