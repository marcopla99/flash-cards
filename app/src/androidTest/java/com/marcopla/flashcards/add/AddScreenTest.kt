package com.marcopla.flashcards.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AddScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun addScreen_fieldsNotEmpty_and_pressAddButton_showInfoMessage() {
        launchAddScreen(composeRule) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            addedCardInfoMessageIsDisplayed()
        }
    }

    @Test
    fun duplicateFlashCard_pressAddButton_showDuplicateErrorMessage() = runTest {
        launchAddScreen(composeRule, DuplicateFlashCardRepository()) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            duplicateErrorMessageIsDisplayed()
        }
    }

    @Test
    fun newFlashCard_successfullyCreated_clearTextFields() {
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
    fun newFlashCard_successfullyCreated_theFrontTextFieldIsFocused() {
        launchAddScreen(composeRule) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            frontTextFieldIsFocused()
        }
    }
}

class DuplicateFlashCardRepository : FlashCardRepository {
    override suspend fun getFlashCards(): List<FlashCard> {
        TODO("Not yet implemented")
    }

    override suspend fun add(newFlashCard: FlashCard) {
        throw DuplicateInsertionException()
    }
}