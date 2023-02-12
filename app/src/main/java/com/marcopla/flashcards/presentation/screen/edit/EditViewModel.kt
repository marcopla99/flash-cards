package com.marcopla.flashcards.presentation.screen.edit

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.domain.use_case.edit.EditFlashCardUseCase
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException
import kotlinx.coroutines.launch

class EditViewModel(private val editFlashCardUseCase: EditFlashCardUseCase) : ViewModel() {

    private val _backTextState = mutableStateOf(EditBackTextState())
    val backTextState: State<EditBackTextState> = _backTextState

    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    private val _screenState = mutableStateOf<EditScreenState>(EditScreenState.Editing)
    val screenState: State<EditScreenState> = _screenState

    fun attemptSubmit(frontText: String, backText: String) {
        viewModelScope.launch {
            try {
                editFlashCardUseCase.invoke(FlashCard(frontText, backText))
                _screenState.value = EditScreenState.Success
            } catch (e: DuplicateInsertionException) {
                _screenState.value = EditScreenState.Error(R.string.duplicateCardError)
            } catch (e: InvalidFrontTextException) {
                _frontTextState.value = _frontTextState.value.copy(showError = true)
            } catch (e: InvalidBackTextException) {
                _backTextState.value = _backTextState.value.copy(showError = true)
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