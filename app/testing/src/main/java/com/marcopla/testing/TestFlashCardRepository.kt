package com.marcopla.testing

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Repository used in tests to mock data retrieval.
 */
class TestFlashCardRepository : FlashCardRepository {
    private val mutableFlow = MutableSharedFlow<List<FlashCard>>()

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return mutableFlow
    }

    override suspend fun add(vararg newFlashCards: FlashCard) {
        // Not used
    }

    override suspend fun edit(flashCard: FlashCard) {
        // Not used
    }

    suspend fun emit(value: List<FlashCard>) {
        mutableFlow.emit(value)
    }
}