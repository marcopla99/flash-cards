package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing_shared.TestFlashCardRepository
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SubmitQuizUseCaseTest {
    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingBlankGuess_thenAddWrongResult_andReturnWrongValidation(blankGuess: String) {
        val repository = TestFlashCardRepository()
        val submitQuizUseCase = SubmitQuizUseCase(repository)

        val isValidationCorrect = submitQuizUseCase.invoke(
            FlashCard("Engels", "English"), blankGuess
        )

        val quizResult = repository.getCurrentResults().first()
        assertFalse(quizResult.isCorrect)
        assertFalse(isValidationCorrect)
    }

    @Test
    fun whenSubmittingWrongGuess_thenAddWrongResult_andReturnWrongValidation() {
        val repository = TestFlashCardRepository()
        val submitQuizUseCase = SubmitQuizUseCase(repository)

        val isValidationCorrect = submitQuizUseCase.invoke(
            FlashCard("Engels", "English"), ":wrongGuess:"
        )

        val quizResult = repository.getCurrentResults().first()
        assertFalse(quizResult.isCorrect)
        assertFalse(isValidationCorrect)
    }

    @Test
    fun whenSubmittingCorrectGuess_thenAddCorrectResult_andReturnCorrectValidation() {
        val repository = TestFlashCardRepository()
        val submitQuizUseCase = SubmitQuizUseCase(repository)

        val isValidationCorrect = submitQuizUseCase.invoke(
            FlashCard("Engels", "English"), "English"
        )

        val quizResult = repository.getCurrentResults().first()
        assertTrue(quizResult.isCorrect)
        assertTrue(isValidationCorrect)
    }
}
