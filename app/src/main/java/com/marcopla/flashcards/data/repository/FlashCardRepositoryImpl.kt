package com.marcopla.flashcards.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.marcopla.flashcards.data.data_source.FlashCardDao
import com.marcopla.flashcards.data.model.FlashCard

class FlashCardRepositoryImpl(
    private val flashCardDao: FlashCardDao
) : FlashCardRepository {

    override suspend fun getFlashCards(): List<FlashCard> {
        return flashCardDao.fetchAll()
    }

    @Throws(DuplicateInsertionException::class)
    override suspend fun add(newFlashCards: FlashCard) {
        try {
            flashCardDao.insert(newFlashCards)
        } catch (_: SQLiteConstraintException) {
            throw DuplicateInsertionException()
        }
    }
}

class DuplicateInsertionException : IllegalStateException()
