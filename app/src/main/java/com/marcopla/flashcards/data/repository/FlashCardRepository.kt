package com.marcopla.flashcards.data.repository

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import kotlinx.coroutines.flow.Flow

interface FlashCardRepository {
    fun getFlashCards(): Flow<List<FlashCard>>

    @Throws(DuplicateInsertionException::class)
    suspend fun add(vararg newFlashCards: FlashCard)

    @Throws(DuplicateInsertionException::class)
    suspend fun edit(flashCard: FlashCard)

    suspend fun getFlashCardById(flashCardId: Int): FlashCard

    suspend fun deleteById(flashCardId: Int)

    fun addResult(quizResult: QuizResult)

    fun getCurrentResults(): Flow<List<QuizResult>>

    fun clearResults()
}