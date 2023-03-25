package com.marcopla.flashcards.data.model

data class QuizResult(
    val flashCard: FlashCard,
    val guess: String,
    val isCorrect: Boolean,
)