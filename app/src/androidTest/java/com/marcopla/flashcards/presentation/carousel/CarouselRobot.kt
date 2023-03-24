package com.marcopla.flashcards.presentation.carousel

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.LoadUseCase
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreen
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import com.marcopla.testing_shared.FakeFlashCardDao

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchCarouselScreen(
    composeRule: ComponentActivityTestRule,
    flashCards: List<FlashCard>,
    block: CarouselScreenRobot.() -> Unit,
): CarouselScreenRobot {
    composeRule.setContent {
        CarouselScreen(
            viewModel = CarouselViewModel(
                LoadUseCase(
                    FlashCardRepositoryImpl(FakeFlashCardDao(flashCards))
                )
            ),
            flashCards = flashCards
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
}

class CarouselScreenVerification(private val composeRule: ComponentActivityTestRule) {
    fun frontTextIsDisplayed(frontText: String) {
        composeRule.onNodeWithText(frontText).assertIsDisplayed()
    }
}