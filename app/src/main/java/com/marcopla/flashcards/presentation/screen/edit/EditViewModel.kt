package com.marcopla.flashcards.presentation.screen.edit

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.domain.use_case.edit.EditUseCase
import kotlinx.coroutines.launch

class EditViewModel(private val editUseCase: EditUseCase) : ViewModel() {

    private val _backTextState = mutableStateOf(EditBackTextState())
    val backTextState: State<EditBackTextState> = _backTextState

    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    private val _screenState = mutableStateOf<EditScreenState>(EditScreenState.Editing)
    val screenState: State<EditScreenState> = _screenState

    fun attemptSubmit(frontText: String, backText: String) {
        if (frontText.isEmpty()) {
            _frontTextState.value = _frontTextState.value.copy(showError = true)
            return
        } else if (backText.isEmpty()) {
            _backTextState.value = _backTextState.value.copy(showError = true)
            return
        }
        viewModelScope.launch {
            try {
                editUseCase.invoke(frontText, backText)
                _screenState.value = EditScreenState.Success
            } catch (e: DuplicateInsertionException) {
                _screenState.value = EditScreenState.Error(R.string.duplicateCardError)
            }
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

sealed interface EditScreenState {
    object Editing : EditScreenState
    object Success : EditScreenState
    data class Error(@StringRes val errorStringRes: Int = -1) : EditScreenState
}