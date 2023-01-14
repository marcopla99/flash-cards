package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.LoadCardsUseCase
import com.marcopla.flashcards.presentation.screen.home.HomeScreen
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchHomeScreen(
    rule: ComponentActivityTestRule,
    repository: FlashCardRepository,
    block: HomeScreenRobot.() -> Unit
): HomeScreenRobot {
    rule.setContent {
        HomeScreen(HomeViewModel(LoadCardsUseCase(repository))) {}
    }
    return HomeScreenRobot(rule).apply(block)
}

class HomeScreenRobot(
    private val rule: ComponentActivityTestRule
) {

    infix fun verify(block: HomeScreenVerification.() -> Unit): HomeScreenVerification {
        return HomeScreenVerification(rule).apply(block)
    }
}

class HomeScreenVerification(
    private val rule: ComponentActivityTestRule
) {
    fun emptyDataTextIsPresent() {
        val emptyDataText = rule.activity.getString(R.string.noFlashCardsCreated)
        rule.onNodeWithText(emptyDataText).assertIsDisplayed()
    }

    fun listOfFlashCardsIsDisplayed(flashCards: List<FlashCard>) {
        flashCards.forEach {
            rule.onNodeWithText(it.frontText).assertIsDisplayed()
        }
    }

    fun showLoadingIndicator() {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.loadingIndicator))
            .assertIsDisplayed()
    }
}