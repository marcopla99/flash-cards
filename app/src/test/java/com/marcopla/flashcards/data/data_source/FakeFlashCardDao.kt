package com.marcopla.flashcards.data.data_source

import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlashCardDao(initialFlashCards: List<FlashCard> = listOf()) : FlashCardDao {
    private val flashCards = initialFlashCards.toMutableList()

    override fun fetchAll(): Flow<List<FlashCard>> {
        return flow { emit(flashCards) }
    }

    override suspend fun insert(flashCard: FlashCard) {
        flashCards.add(flashCard)
    }

    override suspend fun edit(flashCard: FlashCard) {
        TODO()
    }
}