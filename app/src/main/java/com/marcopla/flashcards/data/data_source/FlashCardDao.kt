package com.marcopla.flashcards.data.data_source

import androidx.room.*
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashCard")
    fun fetchAll(): Flow<List<FlashCard>>

    @Insert
    suspend fun insert(vararg flashCards: FlashCard)

    @Update
    suspend fun edit(flashCard: FlashCard)

    @Query("SELECT * FROM flashCard WHERE id = :flashCardId")
    suspend fun fetchById(flashCardId: Int): FlashCard
    @Delete
    fun delete(flashCard: FlashCard)
}
