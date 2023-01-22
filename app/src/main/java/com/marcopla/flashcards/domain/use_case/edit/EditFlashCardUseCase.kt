package com.marcopla.flashcards.domain.use_case.edit

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.add.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.add.InvalidFrontTextException

class EditFlashCardUseCase(private val flashCardRepository: FlashCardRepository) {
    @Throws(
        DuplicateInsertionException::class,
        InvalidFrontTextException::class,
        InvalidBackTextException::class
    )
    suspend fun invoke(flashCard: FlashCard) {
        if (flashCard.frontText.isBlank()) {
            throw InvalidFrontTextException()
        }
        if (flashCard.backText.isBlank()) {
            throw InvalidBackTextException()
        }
        flashCardRepository.edit(flashCard)
    }
}