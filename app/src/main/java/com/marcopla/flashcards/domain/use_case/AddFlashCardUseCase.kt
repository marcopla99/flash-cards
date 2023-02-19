package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException
import javax.inject.Inject

class AddFlashCardUseCase @Inject constructor(
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
