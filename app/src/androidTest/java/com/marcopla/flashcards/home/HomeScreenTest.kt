package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing.TestFlashCardRepository
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val homeScreenTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_whenLaunched_thenShowLoading() {
        launchHomeScreen(homeScreenTestRule, TestFlashCardRepository()) {
            // Empty
        } verify {
            showLoadingIndicator()
        }
    }

    @Test
    fun homeScreen_emptyData_showEmptyMessage() {
        val repository = TestFlashCardRepository(emptyList())

        launchHomeScreen(homeScreenTestRule, repository) {
            // Empty
        } verify {
            emptyDataTextIsPresent()
        }
    }

    @Test
    fun homeScreen_dataIsNotEmpty_showListOfFlashCards() {
        val flashCards = listOf(
            FlashCard("front1", "back1"),
            FlashCard("front2", "back2"),
            FlashCard("front3", "back3"),
        )
        val repository = TestFlashCardRepository(flashCards)

        launchHomeScreen(homeScreenTestRule, repository) {
            // Empty
        } verify {
            listOfFlashCardsIsDisplayed(flashCards)
        }
    }
}