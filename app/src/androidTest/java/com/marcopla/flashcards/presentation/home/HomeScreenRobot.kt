package com.marcopla.flashcards.presentation.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.LoadUseCase
import com.marcopla.flashcards.presentation.screen.home.HomeScreen
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import com.marcopla.testing_shared.FakeFlashCardDao

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

suspend fun launchHomeScreen(
    rule: ComponentActivityTestRule,
    flashCards: List<FlashCard> = emptyList(),
    block: suspend HomeScreenRobot.() -> Unit,
): HomeScreenRobot {
    val repository = FlashCardRepositoryImpl(FakeFlashCardDao(flashCards))
    rule.setContent {
        HomeScreen(
            viewModel = HomeViewModel(LoadUseCase(repository)),
            onNavigateToAddScreen = {},
            onItemClicked = {},
        )
    }
    return HomeScreenRobot(rule).apply { block() }
}

class HomeScreenRobot(
    private val composeTestRule: ComponentActivityTestRule,
) {
    infix fun verify(block: HomeScreenVerification.() -> Unit): HomeScreenVerification {
        return HomeScreenVerification(composeTestRule).apply(block)
    }
}

class HomeScreenVerification(
    private val composeRule: ComponentActivityTestRule,
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

    fun showLoadingIndicator() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.loadingIndicator))
            .assertIsDisplayed()
    }
}