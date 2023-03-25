package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl

class ResultViewModel(repository: FlashCardRepositoryImpl) {

    private val _results = mutableStateOf(repository.getCurrentResults())
    val results: State<List<QuizResult>> = _results
}