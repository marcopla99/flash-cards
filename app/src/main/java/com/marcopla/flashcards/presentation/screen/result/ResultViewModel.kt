package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.domain.usecase.LoadResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val loadResults: LoadResultsUseCase
) : ViewModel() {
    private val _results = mutableStateOf(loadResults())
    val results: State<List<QuizResult>> = _results

    fun clearResults() {
        loadResults.clearAll()
    }
}
