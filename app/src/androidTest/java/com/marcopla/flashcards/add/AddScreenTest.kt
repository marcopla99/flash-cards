package com.marcopla.flashcards.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class AddScreenTest {

    @Inject
    lateinit var repository: FlashCardRepository

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun addScreen_fieldsNotEmpty_and_pressAddButton_showInfoMessage() {
        launchAddScreen(composeRule, repository) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            addedCardInfoMessageIsDisplayed()
        }
    }

    @Test
    fun duplicateFlashCard_pressAddButton_showDuplicateErrorMessage() = runTest {
        repository.add(FlashCard("Engels", "English"))

        launchAddScreen(composeRule, repository) {
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
        launchAddScreen(composeRule, repository) {
            typeTextFront(frontText)
            typeTextBack(backText)
            submit()
        } verify {
            textFieldsAreEmpty(frontText, backText)
        }
    }

    @Test
    fun newFlashCard_successfullyCreated_theFrontTextFieldIsFocused() {
        launchAddScreen(composeRule, repository) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            frontTextFieldIsFocused()
        }
    }
}