package com.marcopla.testing

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestFlashCardRepository(
    initialTestData: List<FlashCard> = emptyList()
) : FlashCardRepository {
    private val flashCards = initialTestData.toMutableList()

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return flow {
            emit(flashCards)
        }
    }

    override suspend fun add(newFlashCard: FlashCard) {
        flashCards.add(newFlashCard)
    }
}