package com.marcopla.flashcards.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.marcopla.flashcards.data.datasource.FlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlashCardRepositoryImpl(
    private val flashCardDao: FlashCardDao
) : FlashCardRepository {

    private val currentResults: MutableList<QuizResult> = mutableListOf()

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return flashCardDao.fetchAll()
    }

    @Throws(DuplicateInsertionException::class)
    override suspend fun add(vararg newFlashCards: FlashCard) {
        try {
            flashCardDao.insert(*newFlashCards)
        } catch (_: SQLiteConstraintException) {
            throw DuplicateInsertionException()
        }
    }

    override suspend fun edit(flashCard: FlashCard) {
        try {
            flashCardDao.edit(flashCard)
        } catch (_: SQLiteConstraintException) {
            throw DuplicateInsertionException()
        }
    }

    override suspend fun getFlashCardById(flashCardId: Int): FlashCard {
        return flashCardDao.fetchById(flashCardId)
    }

    override suspend fun deleteById(flashCardId: Int) {
        flashCardDao.deleteById(flashCardId)
    }

    override fun addResult(quizResult: QuizResult) {
        currentResults.add(quizResult)
    }

    /**
     * Get the stored results for this session.
     * Note: results are currently just saved in memory.
     */
    override fun getCurrentResults(): Flow<List<QuizResult>> {
        return flow { emit(currentResults) }
    }

    override fun clearResults() {
        currentResults.clear()
    }
}

class DuplicateInsertionException : IllegalStateException()
