package com.marcopla.testing

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestFlashCardRepository(
    initialTestData: List<FlashCard> = emptyList()
) : FlashCardRepository {
    private val flashCards = initialTestData.toMutableList()

    private val mutableFlow = MutableSharedFlow<List<FlashCard>>()

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return mutableFlow
    }

    override suspend fun add(newFlashCard: FlashCard) {
        flashCards.add(newFlashCard)
    }

    suspend fun emit(value: List<FlashCard>) {
        mutableFlow.emit(value)
    }
}