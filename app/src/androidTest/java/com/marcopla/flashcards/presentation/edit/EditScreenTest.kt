package com.marcopla.flashcards.presentation.edit

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing_shared.DuplicateFlashCardRepository
import org.junit.Rule
import org.junit.Test

class EditScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun editingFlashCard_whenIsAlreadyStored_thenShowDuplicateError() {
        val selectedFlashCard = FlashCard("Engels", "wrong").apply { id = 0 }

        launchEditScreenFor(selectedFlashCard, composeRule, DuplicateFlashCardRepository()) {
            submit()
        } verify {
            duplicateErrorIsDisplayed()
        }
    }

    @Test
    fun selectedFlashCard_whenDeleteButtonIsClicked_thenShowDialog() {
        val selectedFlashCard = FlashCard("Engels", "English").apply { id = 0 }

        launchEditScreenFor(selectedFlashCard, composeRule) {
            clickDeleteButton()
        } verify {
            dialogIsDisplayed()
        }
    }

    @Test
    fun selectedFlashCard_whenEditing_thenReplaceDeleteButtonWithResetButton() {
        val selectedFlashCard = FlashCard("Engels", "Dutch").apply { id = 0 }

        launchEditScreenFor(selectedFlashCard, composeRule) {
            editBackText("English")
        } verify {
            deleteButtonIsNotDisplayed()
            resetButtonIsDisplayed()
        }
    }

    @Test
    fun selectedFlashCard_whenClickingResetButton_thenShowOriginalTextFields() {
        val originalFlashCard = FlashCard("Engels", "English").apply { id = 0 }
        launchEditScreenFor(originalFlashCard, composeRule) {
            editFrontText("wrong")
            editBackText("wrong")
            clickResetButton()
        } verify {
            deleteButtonIsDisplayed()
            resetButtonIsNotDisplayed()
            originalTextFieldsAreDisplayed(originalFlashCard.frontText, originalFlashCard.backText)
        }
    }
}
