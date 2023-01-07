package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val homeScreenTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_emptyData_showEmptyMessage() {
        launchHomeScreen(homeScreenTestRule) {
            setFlashCards(listOf())
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

        launchHomeScreen(homeScreenTestRule) {
            setFlashCards(flashCards)
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
        launchHomeScreen(homeScreenTestRule) {
            clickAddButton()
        } verify {
            navigatedToAddScreen()
        }
    }
}