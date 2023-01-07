package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val homeScreenTestRule = createAndroidComposeRule<ComponentActivity>()

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

    @Test
    @Ignore(
        "Set HomeScreen as compose content to attempt to fix an issue in " +
            "HomeScreenRobot.clickAddButton that makes the test fail"
    )
    fun homeScreen__navigateToAddScreen() {
        val repository = TestFlashCardRepository()

        launchHomeScreen(homeScreenTestRule, repository) {
            clickAddButton()
        } verify {
            navigatedToAddScreen()
        }
    }
}

class TestFlashCardRepository(
    initialTestData: List<FlashCard> = emptyList()
) : FlashCardRepository {
    private val flashCards = initialTestData.toMutableList()

    override suspend fun getFlashCards(): List<FlashCard> {
        return flashCards
    }

    override suspend fun add(newFlashCard: FlashCard) {
        flashCards.add(newFlashCard)
    }
}