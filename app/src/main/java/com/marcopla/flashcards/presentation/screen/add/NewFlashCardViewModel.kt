package com.marcopla.flashcards.presentation.screen.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marcopla.flashcards.domain.use_case.InvalidBackException
import com.marcopla.flashcards.domain.use_case.InvalidFrontException
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase

class NewFlashCardViewModel(
    private val saveNewCard: SaveNewCardUseCase
) {
    private val _frontTextState = MutableLiveData<FrontTextState>()
    val frontTextState: LiveData<FrontTextState> = _frontTextState

    private val _backTextState = MutableLiveData<BackTextState>()
    val backTextState: LiveData<BackTextState> = _backTextState

    private val _newCardState = MutableLiveData<NewCardState>()
    val newCardState: LiveData<NewCardState> = _newCardState

    fun attemptSubmit(frontText: String?, backText: String?) {
        try {
            saveNewCard(frontText, backText)
            _newCardState.value = NewCardState.Valid
        } catch (exception: IllegalStateException) {
            when (exception) {
                is InvalidFrontException -> {
                    _frontTextState.value = FrontTextState.Invalid
                }
                is InvalidBackException -> {
                    _backTextState.value = BackTextState.Invalid
                }
            }
        }
    }
}

enum class FrontTextState {
    Invalid
}

enum class BackTextState {
    Invalid
}

enum class NewCardState {
    Valid
}