package com.example.marketplace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.compose.ui.test.hasLabelText  // Import necesario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserRegistrationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysRegistrationFields() {
        composeTestRule.setContent {
            UserRegistrationScreen(
                onRegister = { _, _, _ -> },
                onLoginClick = {}
            )
        }

        composeTestRule.onNode(hasLabelText("Full Name")).assertIsDisplayed()
        composeTestRule.onNode(hasLabelText("Email")).assertIsDisplayed()
        composeTestRule.onNode(hasLabelText("Password")).assertIsDisplayed()
        composeTestRule.onNode(hasLabelText("Confirm Password")).assertIsDisplayed()
    }

    @Test
    fun clickingRegisterCallsOnRegister() {
        var registerCalled = false
        var enteredName = ""
        var enteredEmail = ""
        var enteredPassword = ""

        composeTestRule.setContent {
            UserRegistrationScreen(
                onRegister = { name, email, password ->
                    registerCalled = true
                    enteredName = name
                    enteredEmail = email
                    enteredPassword = password
                },
                onLoginClick = {}
            )
        }

        composeTestRule.onNode(hasLabelText("Full Name")).performTextInput("Test User")
        composeTestRule.onNode(hasLabelText("Email")).performTextInput("test@example.com")
        composeTestRule.onNode(hasLabelText("Password")).performTextInput("password")
        composeTestRule.onNode(hasLabelText("Confirm Password")).performTextInput("password")
        composeTestRule.onNodeWithText("Register").performClick()

        assert(registerCalled)
        assert(enteredName == "Test User")
        assert(enteredEmail == "test@example.com")
        assert(enteredPassword == "password")
    }

    @Test
    fun clickingLoginCallsOnLoginClick() {
        var loginClickCalled = false

        composeTestRule.setContent {
            UserRegistrationScreen(
                onRegister = { _, _, _ -> },
                onLoginClick = { loginClickCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Already have an account? Log in").performClick()

        assert(loginClickCalled)
    }
}
