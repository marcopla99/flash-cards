package com.marcopla.testing_shared

import android.os.Build
import androidx.annotation.RequiresApi
import com.marcopla.flashcards.data.data_source.FlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlashCardDao(initialFlashCards: List<FlashCard> = listOf()) : FlashCardDao {
    private val flashCards = initialFlashCards.toMutableList()

    override fun fetchAll(): Flow<List<FlashCard>> {
        return flow { emit(flashCards) }
    }

    override suspend fun insert(vararg flashCards: FlashCard) {
        this.flashCards.addAll(flashCards)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun edit(flashCard: FlashCard) {
        this.flashCards.replaceAll {
            if (it.id == flashCard.id) {
                flashCard
            } else {
                it
            }
        }
    }

    override suspend fun fetchById(flashCardId: Int): FlashCard {
        return flashCards.first { it.id == flashCardId }
    }

    override suspend fun deleteById(flashCardId: Int) {
        flashCards.find { it.id == flashCardId }?.let { flashCards.remove(it) }
    }
}