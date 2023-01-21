package com.marcopla.flashcards.domain.use_case.add

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject

class SaveNewCardUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    @Throws(InvalidBackException::class, InvalidFrontException::class)
    suspend fun invoke(frontText: String?, backText: String?) {
        if (frontText.isNullOrBlank()) {
            throw InvalidFrontException()
        }
        if (backText.isNullOrBlank()) {
            throw InvalidBackException()
        }
        val card = FlashCard(frontText, backText)
        storeCard(card)
    }

    @Throws(DuplicateInsertionException::class)
    private suspend fun storeCard(flashCard: FlashCard) {
        repository.add(flashCard)
    }
}

class InvalidFrontException : IllegalStateException()

class InvalidBackException : IllegalStateException()
