package com.marcopla.flashcards.presentation.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.presentation.screen.home.HomeScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_whenGettingEmptyState_thenShowEmptyMessage() = runTest {
        launchHomeScreen(composeRule, HomeScreenState.Empty) {
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
            FlashCard(frontText = "front3", backText = "back3")
        )

        launchHomeScreen(composeRule, HomeScreenState.Cards(flashCards)) {
            // Do nothing
        } verify {
            listOfFlashCardsIsDisplayed(flashCards)
        }
    }

    @Test
    fun homeScreen_whenNoFlashCards_thenDoNotShowCarouselButton() = runTest {
        launchHomeScreen(composeRule, HomeScreenState.Empty) {
            // Empty
        } verify {
            carouselButtonIsNotDisplayed()
        }
    }
}