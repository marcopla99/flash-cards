package com.marcopla.flashcards.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marcopla.flashcards.data.model.FlashCard

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashCard")
    fun fetchAll(): List<FlashCard>

    @Insert
    fun insert(flashCard: FlashCard)
}
