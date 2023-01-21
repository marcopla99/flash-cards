package com.marcopla.flashcards.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["frontText", "backText"], unique = true)])
data class FlashCard(
    val frontText: String,
    val backText: String
) {
    /**
     * Room auto-generated id for this flash card. Do NOT change its value manually.
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}