package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LoadCardsUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    fun invoke(): Flow<List<FlashCard>> {
        return repository.getFlashCards()
    }
}