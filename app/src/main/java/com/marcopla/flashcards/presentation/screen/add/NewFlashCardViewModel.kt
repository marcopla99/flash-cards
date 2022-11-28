package com.marcopla.flashcards.presentation.screen.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.marcopla.flashcards.domain.use_case.InvalidBackException
import com.marcopla.flashcards.domain.use_case.InvalidFrontException
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase

class NewFlashCardViewModel(
    private val saveNewCard: SaveNewCardUseCase
) {
    private val _frontTextState = mutableStateOf(FrontTextState())
    val frontTextState: State<FrontTextState> = _frontTextState

    private val _backTextState = mutableStateOf(BackTextState())
    val backTextState: State<BackTextState> = _backTextState

    fun attemptSubmit(frontText: String?, backText: String?) {
        try {
            saveNewCard(frontText, backText)
        } catch (exception: IllegalStateException) {
            when (exception) {
                is InvalidFrontException -> {
                    _frontTextState.value = _frontTextState.value.copy(
                        showError = true
                    )
                }
                is InvalidBackException -> {
                    _backTextState.value = _backTextState.value.copy(
                        showError = true
                    )
                }
            }
        }
    }
}

data class FrontTextState(
    val text: String = "",
    val showError: Boolean = false,
)

data class BackTextState(
    val text: String = "",
    val showError: Boolean = false,
)