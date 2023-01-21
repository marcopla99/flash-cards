package com.marcopla.flashcards.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashCard")
    fun fetchAll(): Flow<List<FlashCard>>

    @Insert
    suspend fun insert(flashCard: FlashCard)

    @Update
    suspend fun edit(flashCard: FlashCard)
}
