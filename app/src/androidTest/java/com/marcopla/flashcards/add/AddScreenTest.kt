package com.marcopla.flashcards.add

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.MainActivity
import org.junit.Rule
import org.junit.Test

class AddScreenTest {

    @get:Rule
    val addScreenTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addScreen_fieldsAreEmpty_and_pressTheAddButton_showErrorMessage() {
        launchAddScreen(addScreenTestRule) {
            typeTextFront("English")
            typeTextBack("Engels")
            submit()
        } verify {
            addedCardToastIsDisplayed()
        }
    }
}