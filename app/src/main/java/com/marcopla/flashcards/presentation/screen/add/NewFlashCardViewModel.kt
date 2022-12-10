package com.marcopla.flashcards.presentation.screen.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.domain.use_case.InvalidBackException
import com.marcopla.flashcards.domain.use_case.InvalidFrontException
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NewFlashCardViewModel @Inject constructor(
    private val saveNewCard: SaveNewCardUseCase
) : ViewModel() {

    private val _frontTextState = mutableStateOf(FrontTextState())
    val frontTextState: State<FrontTextState> = _frontTextState

    private val _backTextState = mutableStateOf(BackTextState())
    val backTextState: State<BackTextState> = _backTextState

    private val _errorState = mutableStateOf(AddScreenInfoState.NONE)
    val errorState: State<AddScreenInfoState> = _errorState

    fun attemptSubmit(frontText: String?, backText: String?) {
        viewModelScope.launch {
            try {
                saveNewCard(frontText, backText)
                _errorState.value = AddScreenInfoState.VALID // FIXME: rename _errorState
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
                    is DuplicateInsertionException -> {
                        _errorState.value = AddScreenInfoState.DUPLICATE
                    }
                }
            }
        }
    }

    fun updateFrontText(frontInput: String) {
        _frontTextState.value = _frontTextState.value.copy(text = frontInput, showError = false)
    }

    fun updateBackText(backInput: String) {
        _backTextState.value = _backTextState.value.copy(text = backInput, showError = false)
    }
}

enum class AddScreenInfoState {
    NONE, // FIXME improve
    DUPLICATE,
    VALID
}

data class FrontTextState(
    val text: String = "",
    val showError: Boolean = false,
)

data class BackTextState(
    val text: String = "",
    val showError: Boolean = false,
)