package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlashCardRepository,
) : ViewModel() {
    fun clearResults() {
        repository.clearResults()
    }

    private val _results = mutableStateOf(repository.getCurrentResults())
    val results: State<List<QuizResult>> = _results
}