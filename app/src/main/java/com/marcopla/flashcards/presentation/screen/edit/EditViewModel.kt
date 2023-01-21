package com.marcopla.flashcards.presentation.screen.edit

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository

class EditViewModel(private val editUseCase: EditUseCase) {
    private val _infoState = mutableStateOf(EditInfoState())
    val infoState: State<EditInfoState> = _infoState

    private val _backTextState = mutableStateOf(EditBackTextState())
    val backTextState: State<EditBackTextState> = _backTextState

    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    fun attemptSubmit(frontText: String, backText: String) {
        if (frontText.isEmpty()) {
            _frontTextState.value = _frontTextState.value.copy(showError = true)
            return
        } else if (backText.isEmpty()) {
            _backTextState.value = _backTextState.value.copy(showError = true)
            return
        }
        try {
            editUseCase()
        } catch (e: DuplicateInsertionException) {
            _infoState.value = _infoState.value.copy(errorStringRes = R.string.duplicateCardError)
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

data class EditInfoState(@StringRes val errorStringRes: Int = -1)

class EditUseCase(private val flashCardRepository: FlashCardRepository) {
    operator fun invoke() {
        throw DuplicateInsertionException()
    }
}
