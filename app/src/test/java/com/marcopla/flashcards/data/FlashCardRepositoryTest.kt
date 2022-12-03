package com.marcopla.flashcards.data

import com.marcopla.flashcards.data.repository.FlashCardRepository
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
