package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.home.LoadFlashCardsUseCase
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
        HomeScreen(viewModel = HomeViewModel(LoadFlashCardsUseCase(repository))) {}
    }
    return HomeScreenRobot(rule).apply { block() }
}

class HomeScreenRobot(
    private val rule: ComponentActivityTestRule,
) {
    infix fun verify(block: HomeScreenVerification.() -> Unit): HomeScreenVerification {
        return HomeScreenVerification(rule).apply(block)
    }
}

class HomeScreenVerification(
    private val rule: ComponentActivityTestRule,
) {
    fun emptyMessageIsDisplayed() {
        val emptyMessage = rule.activity.getString(R.string.noFlashCardsCreated)
        rule.onNodeWithText(emptyMessage).assertIsDisplayed()
    }

    fun listOfFlashCardsIsDisplayed(flashCards: List<FlashCard>) {
        flashCards.forEach {
            val itemContentDescription =
                rule.activity.getString(R.string.flashCardItem, it.frontText)
            rule.onNodeWithContentDescription(itemContentDescription)
                .assertIsDisplayed()
        }
    }

    fun showLoadingIndicator() {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.loadingIndicator))
            .assertIsDisplayed()
    }
}