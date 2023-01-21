package com.marcopla.flashcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val frontText: String,
    val backText: String
)