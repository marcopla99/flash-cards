package com.marcopla.flashcards.presentation.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.presentation.screen.home.HomeScreen
import com.marcopla.flashcards.presentation.screen.home.HomeScreenState

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

suspend fun launchHomeScreen(
    rule: ComponentActivityTestRule,
    screenState: HomeScreenState,
    block: suspend HomeScreenRobot.() -> Unit
): HomeScreenRobot {
    rule.setContent {
        HomeScreen(
            screenState = screenState,
            onNavigateToAddScreen = {},
            onItemClicked = {},
            onNavigateToCarouselScreen = {}
        )
    }
    return HomeScreenRobot(rule).apply { block() }
}

class HomeScreenRobot(
    private val composeRule: ComponentActivityTestRule
) {
    infix fun verify(block: HomeScreenVerification.() -> Unit): HomeScreenVerification {
        return HomeScreenVerification(composeRule).apply(block)
    }
}

class HomeScreenVerification(
    private val composeRule: ComponentActivityTestRule
) {
    fun emptyMessageIsDisplayed() {
        val emptyMessage = composeRule.activity.getString(R.string.noFlashCardsCreated)
        composeRule.onNodeWithText(emptyMessage).assertIsDisplayed()
    }

    fun listOfFlashCardsIsDisplayed(flashCards: List<FlashCard>) {
        flashCards.forEach {
            val itemContentDescription =
                composeRule.activity.getString(R.string.flashCardItem, it.frontText)
            composeRule.onNodeWithContentDescription(itemContentDescription)
                .assertIsDisplayed()
        }
    }

    fun carouselButtonIsNotDisplayed() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.carouselButton))
            .assertDoesNotExist()
    }
}