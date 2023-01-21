package com.marcopla.testing

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow

class DuplicateFlashCardRepository : FlashCardRepository {
    override fun getFlashCards(): Flow<List<FlashCard>> {
        TODO("Not used")
    }

    override suspend fun add(newFlashCard: FlashCard) {
        throw DuplicateInsertionException()
    }

    override suspend fun edit(flashCard: FlashCard) {
        throw DuplicateInsertionException()
    }
}