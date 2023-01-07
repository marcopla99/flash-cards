package com.marcopla.flashcards.data.repository

import com.marcopla.flashcards.data.model.FlashCard

interface FlashCardRepository {
    suspend fun getFlashCards(): List<FlashCard>

    @Throws(DuplicateInsertionException::class)
    suspend fun add(newFlashCard: FlashCard)
}