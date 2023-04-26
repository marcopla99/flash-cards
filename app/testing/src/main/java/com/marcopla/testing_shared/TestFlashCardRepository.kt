package com.marcopla.testing_shared

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

/**
 * Repository used in tests to mock data retrieval.
 */
class TestFlashCardRepository : FlashCardRepository {
    private val currentResults: MutableList<QuizResult> = mutableListOf()
    private var flashCards: List<FlashCard> = listOf()
    private val mutableFlow = MutableSharedFlow<List<FlashCard>>()

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return flow {
            emit(flashCards)
        }
    }

    override suspend fun add(vararg newFlashCards: FlashCard) {
        // Not used
    }

    override suspend fun edit(flashCard: FlashCard) {
        // Not used
    }

    override suspend fun getFlashCardById(flashCardId: Int): FlashCard {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(flashCardId: Int) {
        TODO("Not yet implemented")
    }

    override fun addResult(quizResult: QuizResult) {
        currentResults.add(quizResult)
    }

    override fun getCurrentResults(): Flow<List<QuizResult>> {
        return flow { emit(currentResults) }
    }

    override fun clearResults() {
        currentResults.clear()
    }

    suspend fun emit(value: List<FlashCard>) {
        this.flashCards = value
        mutableFlow.emit(value)
    }
}