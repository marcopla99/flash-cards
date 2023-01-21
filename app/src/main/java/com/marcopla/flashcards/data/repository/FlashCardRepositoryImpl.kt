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
    override suspend fun add(newFlashCard: FlashCard) {
        try {
            flashCardDao.insert(newFlashCard)
        } catch (_: SQLiteConstraintException) {
            throw DuplicateInsertionException()
        }
    }

    override suspend fun edit(flashCard: FlashCard) {
        flashCardDao.edit(flashCard)
    }
}

class DuplicateInsertionException : IllegalStateException()
