package com.marcopla.flashcards.presentation.result

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.presentation.screen.result.ResultItem

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchResultItem(
    composeRule: ComponentActivityTestRule,
    quizResult: QuizResult,
    block: ResultItemRobot.() -> Unit,
): ResultItemRobot {
    composeRule.setContent {
        ResultItem(quizResult = quizResult)
    }
    return ResultItemRobot(composeRule).apply(block)
}

class ResultItemRobot(private val composeRule: ComponentActivityTestRule) {
    infix fun verify(block: ResultItemVerification.() -> Unit): ResultItemVerification {
        return ResultItemVerification(composeRule).apply(block)
    }
}

class ResultItemVerification(private val composeRule: ComponentActivityTestRule) {
    fun flashCardIsDisplayed(flashCard: FlashCard) {
        composeRule.onNodeWithText(flashCard.frontText).assertIsDisplayed()
        composeRule.onNodeWithText(flashCard.backText).assertIsDisplayed()
    }

    fun guessResponseIsDisplayed(guess: String) {
        composeRule.onNodeWithText(guess).assertIsDisplayed()
    }
}
