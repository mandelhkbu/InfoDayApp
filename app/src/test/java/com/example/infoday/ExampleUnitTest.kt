package com.example.infoday

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MyUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun verifyMindDriveIsSavedAndDisplayed() {

        // Set the content of the screen
        composeTestRule.setContent {
            ScaffoldScreen()
        }

        // Perform UI interactions and assertions
        composeTestRule.onNodeWithText("Events").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("Comp", substring = true).assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("MindDrive", substring = true).assertIsDisplayed()
            .performTouchInput { longClick() }
        composeTestRule.onNodeWithText("Itin").performClick()
        composeTestRule.onNodeWithText("MindDrive", substring = true).assertIsDisplayed()
    }
}