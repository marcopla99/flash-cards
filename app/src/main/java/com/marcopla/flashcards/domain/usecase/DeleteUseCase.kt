package com.marcopla.flashcards.domain.usecase

import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(private val repository: FlashCardRepository) {
    suspend fun invoke(flashCardId: Int) {
        repository.deleteById(flashCardId)
    }
}