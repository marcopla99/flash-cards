package com.marcopla.flashcards.domain.use_case.home

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadFlashCardsUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    fun invoke(): Flow<List<FlashCard>> {
        return repository.getFlashCards()
    }
}