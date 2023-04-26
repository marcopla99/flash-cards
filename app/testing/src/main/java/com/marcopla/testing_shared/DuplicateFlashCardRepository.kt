package com.marcopla.testing_shared

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow

class DuplicateFlashCardRepository : FlashCardRepository {
    override fun getFlashCards(): Flow<List<FlashCard>> {
        TODO("Not used")
    }

    override suspend fun add(vararg newFlashCards: FlashCard) {
        throw DuplicateInsertionException()
    }

    override suspend fun edit(flashCard: FlashCard) {
        throw DuplicateInsertionException()
    }

    override suspend fun getFlashCardById(flashCardId: Int): FlashCard {
        TODO("Not used")
    }

    override suspend fun deleteById(flashCardId: Int) {
        TODO("Not yet implemented")
    }

    override fun addResult(quizResult: QuizResult) {
        TODO("Not yet implemented")
    }

    override fun getCurrentResults(): Flow<List<QuizResult>> {
        TODO("Not yet implemented")
    }

    override fun clearResults() {
        TODO("Not yet implemented")
    }
}