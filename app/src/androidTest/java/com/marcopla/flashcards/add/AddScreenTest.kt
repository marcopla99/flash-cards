package com.marcopla.flashcards.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class AddScreenTest {

    @get:Rule
    val addScreenTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun addScreen_fieldsNotEmpty_and_pressAddButton_showSnackbarMessage() {
        launchAddScreen(addScreenTestRule) {
            typeTextFront("English")
            typeTextBack("Engels")
            submit()
        } verify {
            addedCardSnackbarIsDisplayed()
        }
    }
}