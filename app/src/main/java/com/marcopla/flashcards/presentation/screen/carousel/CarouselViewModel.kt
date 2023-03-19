package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class CarouselViewModel {
    private val _screenState = mutableStateOf(CarouselScreenState.EMPTY)
    val screenState: State<CarouselScreenState> = _screenState

    fun submit(userGuess: String) {
        _screenState.value = CarouselScreenState.ERROR
    }
}

enum class CarouselScreenState {
    EMPTY,
    ERROR
}