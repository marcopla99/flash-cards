package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.marcopla.flashcards.data.model.FlashCard

class CarouselViewModel(
    private val flashCards: List<FlashCard>,
) {
    private var currentFlashCardIndex = 0

    private val _currentFlashCard = mutableStateOf(flashCards[currentFlashCardIndex])
    val currentFlashCard: State<FlashCard> = _currentFlashCard

    private val _screenState = mutableStateOf<CarouselScreenState>(CarouselScreenState.Empty)
    val screenState: State<CarouselScreenState> = _screenState

    fun submit(userGuess: String) {
        if (userGuess != currentFlashCard.value.backText) {
            _screenState.value = CarouselScreenState.Error
        } else {
            _screenState.value = CarouselScreenState.Success
        }

        if (currentFlashCard.value != flashCards.last()) {
            currentFlashCardIndex += 1
            _currentFlashCard.value = flashCards[currentFlashCardIndex]
        } else {
            _screenState.value = CarouselScreenState.Finished
        }
    }

    fun loadFlashCards() {
        _screenState.value = CarouselScreenState.Loaded(flashCards)
    }
}

sealed interface CarouselScreenState {
    data class Loaded(val flashCards: List<FlashCard>) : CarouselScreenState
    object Empty : CarouselScreenState
    object Error : CarouselScreenState
    object Success : CarouselScreenState
    object Finished : CarouselScreenState
}