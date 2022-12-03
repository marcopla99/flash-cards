package com.marcopla.flashcards.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FlashCardRepositoryTest {

    @Test
    fun validNewFlashCard_afterIsInserted_isPossibleToReadIt() {
        val newFlashCards = FlashCard("Engels", "English")
        val repository = FlashCardRepository()

        repository.add(newFlashCards)

        assertEquals(true, repository.getFlashCards().contains(newFlashCards))
    }
}

class FlashCardRepository {
    private val flashCardsList = mutableListOf<FlashCard>()

    fun getFlashCards(): List<FlashCard> {
        return flashCardsList
    }

    fun add(newFlashCards: FlashCard) {
        flashCardsList.add(newFlashCards)
    }
}
