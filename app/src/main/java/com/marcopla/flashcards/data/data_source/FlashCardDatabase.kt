package com.marcopla.flashcards.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marcopla.flashcards.data.model.FlashCard

@Database(entities = [FlashCard::class], version = 1)
abstract class FlashCardDatabase : RoomDatabase() {
    abstract val flashCardDao: FlashCardDao
}
