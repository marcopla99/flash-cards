package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject

class LoadCardsUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    suspend fun invoke(): List<FlashCard> {
        return repository.getFlashCards()
    }
}