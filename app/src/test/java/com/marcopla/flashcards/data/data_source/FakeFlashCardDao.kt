package com.marcopla.flashcards.data.data_source

import com.marcopla.flashcards.data.model.FlashCard

class FakeFlashCardDao(initialFlashCards: List<FlashCard> = listOf()) : FlashCardDao {
    private val flashCards = initialFlashCards.toMutableList()

    override suspend fun fetchAll(): List<FlashCard> {
        return flashCards
    }

    override suspend fun insert(flashCard: FlashCard) {
        flashCards.add(flashCard)
    }
}