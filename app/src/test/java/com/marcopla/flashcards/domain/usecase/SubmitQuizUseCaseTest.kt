package com.marcopla.flashcards.domain.usecase

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing_shared.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
class SubmitQuizUseCaseTest {
    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingBlankGuess_thenAddWrongResult_andReturnWrongValidation(blankGuess: String) =
        runTest {
            val repository = TestFlashCardRepository()
            val submitQuizUseCase = SubmitQuizUseCase(repository)

            val isValidationCorrect = submitQuizUseCase.invoke(
                FlashCard("Engels", "English"),
                blankGuess
            )

            val quizResult = repository.getCurrentResults().first().first()
            assertFalse(quizResult.isCorrect)
            assertFalse(isValidationCorrect)
        }

    @Test
    fun whenSubmittingWrongGuess_thenAddWrongResult_andReturnWrongValidation() = runTest {
        val repository = TestFlashCardRepository()
        val submitQuizUseCase = SubmitQuizUseCase(repository)

        val isValidationCorrect = submitQuizUseCase.invoke(
            FlashCard("Engels", "English"),
            ":wrongGuess:"
        )

        val quizResult = repository.getCurrentResults().first().first()
        assertFalse(quizResult.isCorrect)
        assertFalse(isValidationCorrect)
    }

    @Test
    fun whenSubmittingCorrectGuess_thenAddCorrectResult_andReturnCorrectValidation() = runTest {
        val repository = TestFlashCardRepository()
        val submitQuizUseCase = SubmitQuizUseCase(repository)

        val isValidationCorrect = submitQuizUseCase.invoke(
            FlashCard("Engels", "English"),
            "English"
        )

        val quizResult = repository.getCurrentResults().first().first()
        assertTrue(quizResult.isCorrect)
        assertTrue(isValidationCorrect)
    }
}
