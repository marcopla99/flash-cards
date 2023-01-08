package com.marcopla.testing

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository

class TestFlashCardRepository(
    initialTestData: List<FlashCard> = emptyList()
) : FlashCardRepository {
    private val flashCards = initialTestData.toMutableList()

    override suspend fun getFlashCards(): List<FlashCard> {
        return flashCards
    }

    override suspend fun add(newFlashCard: FlashCard) {
        flashCards.add(newFlashCard)
    }
}