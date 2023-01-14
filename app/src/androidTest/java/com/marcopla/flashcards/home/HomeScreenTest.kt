package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_whenLaunched_thenShowLoading() = runTest {
        launchHomeScreen(composeTestRule) {
            // Empty
        } verify {
            showLoadingIndicator()
        }
    }

    @Test
    fun homeScreen_whenGettingEmptyState_showEmptyMessage() = runTest {
        launchHomeScreen(composeTestRule) {
            waitForEmptyDataToLoad()
        } verify {
            emptyMessageIsDisplayed()
        }
    }

    @Test
    fun homeScreen_whenDataIsNotEmpty_showListOfFlashCards() = runTest {
        val flashCards = listOf(
            FlashCard("front1", "back1"),
            FlashCard("front2", "back2"),
            FlashCard("front3", "back3"),
        )

        launchHomeScreen(composeTestRule) {
            waitForFlashCardsToLoad(flashCards)
        } verify {
            listOfFlashCardsIsDisplayed(flashCards)
        }
    }
}