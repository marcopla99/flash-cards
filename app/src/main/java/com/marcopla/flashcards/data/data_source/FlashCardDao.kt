package com.marcopla.flashcards.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marcopla.flashcards.data.model.FlashCard

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashCard")
    suspend fun fetchAll(): List<FlashCard>

    @Insert
    suspend fun insert(flashCard: FlashCard)
}
