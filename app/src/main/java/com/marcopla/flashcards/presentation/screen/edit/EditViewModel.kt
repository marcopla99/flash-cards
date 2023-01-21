package com.marcopla.flashcards.presentation.screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class EditViewModel {
    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    fun attemptSubmit(frontText: String, backText: String) {
        _frontTextState.value = _frontTextState.value.copy(showError = true)
    }
}

data class EditFrontTextState(
    val value: String = "",
    val showError: Boolean = false,
)