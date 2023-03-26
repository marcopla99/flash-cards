package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject

class SubmitQuizUseCase @Inject constructor(private val repository: FlashCardRepository) {
    operator fun invoke(flashCard: FlashCard, userGuess: String): Boolean {
        val isCorrect = flashCard.backText == userGuess
        repository.addResult(
            QuizResult(
                flashCard = flashCard,
                guess = userGuess,
                isCorrect = isCorrect,
            ),
        )
        return isCorrect
    }
}