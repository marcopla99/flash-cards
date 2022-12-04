package com.marcopla.flashcards.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.marcopla.flashcards.data.data_source.FlashCardDao
import com.marcopla.flashcards.data.model.FlashCard

class FlashCardRepository(
    private val flashCardDao: FlashCardDao
) {

    fun getFlashCards(): List<FlashCard> {
        return flashCardDao.fetchAll()
    }

    fun add(newFlashCards: FlashCard) {
        try {
            flashCardDao.insert(newFlashCards)
        } catch (_: SQLiteConstraintException) {
        }
    }
}