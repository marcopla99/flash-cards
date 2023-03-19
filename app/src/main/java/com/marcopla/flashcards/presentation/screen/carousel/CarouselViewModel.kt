package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.marcopla.flashcards.data.model.FlashCard

class CarouselViewModel(private val flashCards: List<FlashCard>) {
    private val _screenState = mutableStateOf<CarouselScreenState>(CarouselScreenState.Empty)
    val screenState: State<CarouselScreenState> = _screenState

    fun submit(userGuess: String) {
        _screenState.value = CarouselScreenState.Error
    }

    fun loadFlashCards() {
        _screenState.value = CarouselScreenState.Loaded(flashCards)
    }
}

sealed interface CarouselScreenState {
    data class Loaded(val flashCards: List<FlashCard>) : CarouselScreenState
    object Empty : CarouselScreenState
    object Error : CarouselScreenState
}