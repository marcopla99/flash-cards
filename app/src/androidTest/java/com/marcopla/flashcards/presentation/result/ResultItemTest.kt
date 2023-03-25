package com.marcopla.flashcards.presentation.result

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import org.junit.Rule
import org.junit.Test

class ResultItemTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenDisplayingWrongResult_thenShowFlashCardWithGuess() {
        val quizResult = QuizResult(
            FlashCard("Engels", "English"),
            "wrong",
            false
        )
        launchResultItem(composeRule, quizResult) {
            // Empty
        } verify {
            flashCardIsDisplayed(quizResult.flashCard)
            guessResponseIsDisplayed(quizResult.guess)
        }
    }
}