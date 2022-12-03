package com.marcopla.flashcards.data.repository

import com.marcopla.flashcards.data.model.FlashCard

class FlashCardRepository {
    private val flashCardsList = mutableListOf<FlashCard>()

    fun getFlashCards(): List<FlashCard> {
        return flashCardsList
    }

    fun add(newFlashCards: FlashCard) {
        flashCardsList.add(newFlashCards)
    }
}