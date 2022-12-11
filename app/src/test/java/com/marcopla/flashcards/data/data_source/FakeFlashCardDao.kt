package com.marcopla.flashcards.data.data_source

import com.marcopla.flashcards.data.model.FlashCard

class FakeFlashCardDao : FlashCardDao {
    private val _flashCards: MutableList<FlashCard> = mutableListOf()
    private val flashCards: List<FlashCard> = _flashCards

    override suspend fun fetchAll(): List<FlashCard> {
        return flashCards
    }

    override suspend fun insert(flashCard: FlashCard) {
        _flashCards.add(flashCard)
    }
}