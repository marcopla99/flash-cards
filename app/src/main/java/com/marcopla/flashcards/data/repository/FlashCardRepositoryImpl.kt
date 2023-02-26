package com.marcopla.flashcards.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.marcopla.flashcards.data.data_source.FlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow

class FlashCardRepositoryImpl(
    private val flashCardDao: FlashCardDao
) : FlashCardRepository {

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

    override fun delete(flashCard: FlashCard) {
        flashCardDao.delete(flashCard)
    }
}

class DuplicateInsertionException : IllegalStateException()
