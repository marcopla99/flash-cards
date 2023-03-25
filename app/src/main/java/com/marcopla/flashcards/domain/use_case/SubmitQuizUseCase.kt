package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepository

class SubmitQuizUseCase(private val repository: FlashCardRepository) {
    fun invoke(flashCard: FlashCard, userGuess: String) {
        repository.addResult(
            QuizResult(
                flashCard = flashCard,
                guess = userGuess,
                isCorrect = flashCard.backText == userGuess,
            ),
        )
    }
}