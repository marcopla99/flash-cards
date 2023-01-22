package com.marcopla.flashcards.domain.use_case.add

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject

class AddFlashCardsUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    @Throws(InvalidBackTextException::class, InvalidFrontTextException::class)
    suspend fun invoke(frontText: String?, backText: String?) {
        if (frontText.isNullOrBlank()) {
            throw InvalidFrontTextException()
        }
        if (backText.isNullOrBlank()) {
            throw InvalidBackTextException()
        }
        val flashCard = FlashCard(frontText = frontText, backText = backText)
        storeFlashCard(flashCard)
    }

    @Throws(DuplicateInsertionException::class)
    private suspend fun storeFlashCard(flashCard: FlashCard) {
        repository.add(flashCard)
    }
}

class InvalidFrontTextException : IllegalStateException()

class InvalidBackTextException : IllegalStateException()
