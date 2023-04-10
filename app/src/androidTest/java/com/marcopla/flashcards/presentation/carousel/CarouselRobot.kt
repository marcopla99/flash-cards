package com.marcopla.flashcards.presentation.carousel

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.usecase.LoadUseCase
import com.marcopla.flashcards.domain.usecase.SubmitQuizUseCase
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreen
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import com.marcopla.testing_shared.FakeFlashCardDao

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchCarouselScreen(
    composeRule: ComponentActivityTestRule,
    flashCards: List<FlashCard>,
    block: CarouselScreenRobot.() -> Unit
): CarouselScreenRobot {
    composeRule.setContent {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao(flashCards))
        CarouselScreen(
            viewModel = CarouselViewModel(
                LoadUseCase(repository),
                SubmitQuizUseCase(repository)
            )
        ) {}
    }
    return CarouselScreenRobot(composeRule).apply(block)
}

class CarouselScreenRobot(private val composeRule: ComponentActivityTestRule) {
    infix fun verify(block: CarouselScreenVerification.() -> Unit): CarouselScreenVerification {
        return CarouselScreenVerification(composeRule).apply(block)
    }

    fun clickNextButton() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.nextButton))
            .performClick()
    }

    fun inputGuess(inputGuess: String) {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.guessField))
            .performTextInput(inputGuess)
    }
}

class CarouselScreenVerification(private val composeRule: ComponentActivityTestRule) {
    fun frontTextIsDisplayed(frontText: String) {
        composeRule.onNodeWithText(frontText).assertIsDisplayed()
    }
}