package com.marcopla.flashcards.presentation.home

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
        composeTestRule.mainClock.autoAdvance = false

        launchHomeScreen(composeTestRule) {
            // Do nothing
        } verify {
            showLoadingIndicator()
        }
    }

    @Test
    fun homeScreen_whenGettingEmptyState_thenShowEmptyMessage() = runTest {
        launchHomeScreen(composeTestRule, emptyList()) {
            // Do nothing
        } verify {
            emptyMessageIsDisplayed()
        }
    }

    @Test
    fun homeScreen_whenDataIsNotEmpty_thenShowListOfFlashCards() = runTest {
        val flashCards = listOf(
            FlashCard(frontText = "front1", backText = "back1"),
            FlashCard(frontText = "front2", backText = "back2"),
            FlashCard(frontText = "front3", backText = "back3"),
        )

        launchHomeScreen(composeTestRule, flashCards) {
            // Do nothing
        } verify {
            listOfFlashCardsIsDisplayed(flashCards)
        }
    }
}