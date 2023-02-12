package com.marcopla.testing_shared

import com.marcopla.flashcards.data.data_source.FlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlashCardDao(initialFlashCards: List<FlashCard> = listOf()) : FlashCardDao {
    private val flashCards = initialFlashCards.toMutableList()

    override fun fetchAll(): Flow<List<FlashCard>> {
        return flow { emit(flashCards) }
    }

    override suspend fun insert(vararg flashCards: FlashCard) {
        this.flashCards.addAll(flashCards)
    }

    override suspend fun edit(flashCard: FlashCard) {
        this.flashCards.replaceAll {
            if (it.id == flashCard.id) {
                flashCard
            } else {
                it
            }
        }
    }
}