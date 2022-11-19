package com.marcopla.flashcards.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class AddScreenTest {

    @get:Rule
    val addScreenTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun addScreen_fieldsAreEmpty_and_pressTheAddButton_showErrorMessage() {
        launchAddScreen(addScreenTestRule) {
            typeTextFront("English")
            typeTextBack("Engels")
            submit()
        } verify {
            addedCardSnackbarIsDisplayed()
        }
    }
}