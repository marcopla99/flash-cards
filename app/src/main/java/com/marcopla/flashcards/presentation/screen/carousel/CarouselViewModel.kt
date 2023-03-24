package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CarouselViewModel(
    private val loadUseCase: LoadUseCase
) : ViewModel() {
    private var flashCards: List<FlashCard> = emptyList()

    private var currentFlashCardIndex = 0

    private val _screenState = mutableStateOf<CarouselScreenState>(CarouselScreenState.Empty)
    val screenState: State<CarouselScreenState> = _screenState

    fun submit(userGuess: String) {
        val isNotCorrect = userGuess != flashCards[currentFlashCardIndex].backText
        if (isNotCorrect) {
            _screenState.value =
                CarouselScreenState.Error(flashCards[currentFlashCardIndex])
        } else {
            _screenState.value =
                CarouselScreenState.Success(flashCards[currentFlashCardIndex])
        }

        if (flashCards[currentFlashCardIndex] != flashCards.last()) {
            currentFlashCardIndex += 1
            if (isNotCorrect) {
                _screenState.value =
                    CarouselScreenState.Error(flashCards[currentFlashCardIndex])
            } else {
                _screenState.value =
                    CarouselScreenState.Success(flashCards[currentFlashCardIndex])
            }
        } else {
            _screenState.value = CarouselScreenState.Finished
        }
    }

    fun loadFlashCards() {
        viewModelScope.launch {
            flashCards = loadUseCase.loadAll().first()
            _screenState.value = CarouselScreenState.Loaded(flashCards[0])
        }
    }
}

sealed interface CarouselScreenState {
    data class Loaded(val flashCard: FlashCard) : CarouselScreenState
    object Empty : CarouselScreenState
    data class Error(val nextFlashCard: FlashCard) : CarouselScreenState
    data class Success(val nextFlashCard: FlashCard) : CarouselScreenState
    object Finished : CarouselScreenState
}