package com.marcopla.flashcards.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["frontText", "backText"])
data class FlashCard(
    val frontText: String,
    val backText: String
)