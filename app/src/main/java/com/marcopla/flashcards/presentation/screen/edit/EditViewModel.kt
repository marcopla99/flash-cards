package com.marcopla.flashcards.presentation.screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class EditViewModel {
    private val _backTextState = mutableStateOf(EditBackTextState())
    val backTextState: State<EditBackTextState> = _backTextState

    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    fun attemptSubmit(frontText: String, backText: String) {
        if (frontText.isEmpty()) {
            _frontTextState.value = _frontTextState.value.copy(showError = true)
        } else if (backText.isEmpty()) {
            _backTextState.value = _backTextState.value.copy(showError = true)
        }
    }
}

data class EditFrontTextState(
    val value: String = "",
    val showError: Boolean = false,
)

data class EditBackTextState(
    val value: String = "",
    val showError: Boolean = false,
)