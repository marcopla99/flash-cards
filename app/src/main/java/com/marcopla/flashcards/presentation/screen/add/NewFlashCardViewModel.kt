package com.marcopla.flashcards.presentation.screen.add

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.R
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

    private val _infoTextState = mutableStateOf(InfoTextState())
    val infoTextState: State<InfoTextState> = _infoTextState

    private val _screenState = mutableStateOf(ScreenState.FAILED_SAVE)
    val screenState: State<ScreenState> = _screenState

    fun attemptSubmit(frontText: String?, backText: String?) {
        viewModelScope.launch {
            try {
                saveNewCard(frontText, backText)
                handleSuccessState()
            } catch (exception: IllegalStateException) {
                handleFailureState(exception)
            }
        }
    }

    private fun handleSuccessState() {
        _screenState.value = ScreenState.SUCCESSFUL_SAVE
        _infoTextState.value = _infoTextState.value.copy(
            messageStringRes = R.string.cardAdded
        )
        _frontTextState.value = _frontTextState.value.copy(text = "")
        _backTextState.value = _backTextState.value.copy(text = "")
    }

    private fun handleFailureState(exception: IllegalStateException) {
        _screenState.value = ScreenState.FAILED_SAVE
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
                _infoTextState.value = _infoTextState.value.copy(
                    messageStringRes = R.string.duplicateCardError
                )
            }
        }
    }

    fun updateFrontText(frontInput: String) {
        setEditingState()
        _frontTextState.value = _frontTextState.value.copy(text = frontInput, showError = false)
    }

    fun updateBackText(backInput: String) {
        setEditingState()
        _backTextState.value = _backTextState.value.copy(text = backInput, showError = false)
    }

    private fun setEditingState() {
        _screenState.value = ScreenState.EDITING
        _infoTextState.value = _infoTextState.value.copy(
            messageStringRes = null
        )
    }
}

enum class ScreenState {
    SUCCESSFUL_SAVE,
    FAILED_SAVE,
    EDITING
}

data class InfoTextState(
    @StringRes val messageStringRes: Int? = null
)

data class FrontTextState(
    val text: String = "",
    val showError: Boolean = false,
)

data class BackTextState(
    val text: String = "",
    val showError: Boolean = false,
)