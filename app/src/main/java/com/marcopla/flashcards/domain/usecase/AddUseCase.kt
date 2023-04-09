package com.marcopla.flashcards.domain.usecase

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.usecase.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.usecase.exceptions.InvalidFrontTextException
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    @Throws(
        InvalidFrontTextException::class,
        InvalidBackTextException::class,
        DuplicateInsertionException::class,
    )
    suspend fun invoke(frontText: String?, backText: String?) {
        if (frontText.isNullOrBlank()) {
            throw InvalidFrontTextException()
        }
        if (backText.isNullOrBlank()) {
            throw InvalidBackTextException()
        }
        val flashCard = FlashCard(frontText = frontText, backText = backText)
        repository.add(flashCard)
    }
}
