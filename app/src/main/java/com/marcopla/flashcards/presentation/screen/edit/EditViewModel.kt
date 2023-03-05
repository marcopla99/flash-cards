package com.marcopla.flashcards.presentation.screen.edit

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.domain.use_case.DeleteUseCase
import com.marcopla.flashcards.domain.use_case.EditFlashCardUseCase
import com.marcopla.flashcards.domain.use_case.LoadFlashCardsUseCase
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException
import com.marcopla.flashcards.presentation.navigation.FLASH_CARD_ID_ARG_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val editFlashCardUseCase: EditFlashCardUseCase,
    private val loadFlashCardUseCase: LoadFlashCardsUseCase,
    private val deleteUseCase: DeleteUseCase,
) : ViewModel() {

    private val _backTextState = mutableStateOf(EditBackTextState())
    val backTextState: State<EditBackTextState> = _backTextState

    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    private val _screenState = mutableStateOf<EditScreenState>(EditScreenState.Initial)
    val screenState: State<EditScreenState> = _screenState

    private val _shouldShowDeleteConfirmation = mutableStateOf(false)
    val shouldShowDeleteConfirmation = _shouldShowDeleteConfirmation

    private val flashCardId: Int by lazy { checkNotNull(savedStateHandle[FLASH_CARD_ID_ARG_KEY]) }

    fun initState() {
        viewModelScope.launch {
            val flashCard = loadFlashCardUseCase.loadById(flashCardId)
            _frontTextState.value = _frontTextState.value.copy(text = flashCard.frontText)
            _backTextState.value = _backTextState.value.copy(text = flashCard.backText)
        }
    }

    fun attemptSubmit(frontText: String, backText: String) {
        viewModelScope.launch {
            try {
                editFlashCardUseCase.invoke(
                    FlashCard(frontText, backText).apply { id = this@EditViewModel.flashCardId }
                )
                _screenState.value = EditScreenState.Edited
            } catch (e: DuplicateInsertionException) {
                _screenState.value = EditScreenState.Error(R.string.duplicateCardError)
            } catch (e: InvalidFrontTextException) {
                _frontTextState.value = _frontTextState.value.copy(showError = true)
            } catch (e: InvalidBackTextException) {
                _backTextState.value = _backTextState.value.copy(showError = true)
            }
        }
    }

    fun updateFrontText(newText: String) {
        _screenState.value = EditScreenState.Editing
        _frontTextState.value = _frontTextState.value.copy(text = newText, showError = false)
    }

    fun updateBackText(newText: String) {
        _screenState.value = EditScreenState.Editing
        _backTextState.value = _backTextState.value.copy(text = newText, showError = false)
    }

    fun delete() {
        viewModelScope.launch {
            deleteUseCase.invoke(
                FlashCard(
                    frontTextState.value.text,
                    backTextState.value.text,
                ).apply { id = flashCardId }
            )
            _screenState.value = EditScreenState.Deleted
        }
    }

    fun hideDeleteConfirmationDialog() {
        _shouldShowDeleteConfirmation.value = false
    }

    fun showDeleteConfirmationDialog() {
        _shouldShowDeleteConfirmation.value = true
    }
}

data class EditFrontTextState(
    val text: String = "",
    val showError: Boolean = false,
)

data class EditBackTextState(
    val text: String = "",
    val showError: Boolean = false,
)

sealed interface EditScreenState {
    object Initial : EditScreenState
    object Edited : EditScreenState
    object Deleted : EditScreenState
    object Editing : EditScreenState
    data class Error(@StringRes val errorStringRes: Int = -1) : EditScreenState
}