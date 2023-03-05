package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException
import javax.inject.Inject

class EditUseCase @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
) {
    @Throws(
        InvalidFrontTextException::class,
        InvalidBackTextException::class,
        DuplicateInsertionException::class,
    )
    suspend fun invoke(frontText: String, backText: String, flashCardId: Int) {
        if (frontText.isBlank()) {
            throw InvalidFrontTextException()
        }
        if (backText.isBlank()) {
            throw InvalidBackTextException()
        }
        val editedFlashCard = FlashCard(frontText, backText).apply { id = flashCardId }
        flashCardRepository.edit(editedFlashCard)
    }
}