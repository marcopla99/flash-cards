package com.marcopla.flashcards.presentation.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.testing_shared.DuplicateFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun addScreen_whenFieldsNotEmpty_andPressAddButton_thenShowInfoMessage() {
        launchAddScreen(composeRule) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            addedCardInfoMessageIsDisplayed()
        }
    }

    @Test
    fun duplicateFlashCard_whenPressAddButton_thenShowDuplicateErrorMessage() = runTest {
        launchAddScreen(composeRule, DuplicateFlashCardRepository()) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            duplicateErrorMessageIsDisplayed()
        }
    }

    @Test
    fun newFlashCard_whenSuccessfullyCreated_thenClearTextFields() {
        val frontText = "Nieuwe"
        val backText = "New"

        launchAddScreen(composeRule) {
            typeTextFront(frontText)
            typeTextBack(backText)
            submit()
        } verify {
            textFieldsAreEmpty(frontText, backText)
        }
    }

    @Test
    fun newFlashCard_whenSuccessfullyCreated_thenTheFrontTextFieldIsFocused() {
        launchAddScreen(composeRule) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            frontTextFieldIsFocused()
        }
    }
}