package com.marcopla.flashcards.data.repository

import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow

interface FlashCardRepository {
    fun getFlashCards(): Flow<List<FlashCard>>

    @Throws(DuplicateInsertionException::class)
    suspend fun add(vararg newFlashCards: FlashCard)

    @Throws(DuplicateInsertionException::class)
    suspend fun edit(flashCard: FlashCard)

    suspend fun getFlashCardById(flashCardId: Int): FlashCard

    fun delete(flashCard: FlashCard)
}