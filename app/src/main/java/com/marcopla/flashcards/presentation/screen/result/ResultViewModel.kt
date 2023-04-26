package com.marcopla.flashcards.presentation.screen.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.domain.usecase.LoadResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val loadResults: LoadResultsUseCase
) : ViewModel() {
    val results: StateFlow<List<QuizResult>> = loadResults().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun clearResults() {
        loadResults.clearAll()
    }
}
