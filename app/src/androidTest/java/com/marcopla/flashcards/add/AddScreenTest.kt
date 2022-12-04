package com.marcopla.flashcards.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AddScreenTest {

    @Inject
    lateinit var repository: FlashCardRepository

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val addScreenTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun addScreen_fieldsNotEmpty_and_pressAddButton_showSnackbarMessage() {
        launchAddScreen(addScreenTestRule) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            addedCardSnackbarIsDisplayed()
        }
    }

    @Test
    fun duplicateFlashCard_pressAddButton_showDuplicateErrorMessage() {
        repository.add(FlashCard("Engels", "English"))
        launchAddScreen(addScreenTestRule) {
            typeTextFront("Engels")
            typeTextBack("English")
            submit()
        } verify {
            duplicateErrorMessageIsDisplayed()
        }
    }
}