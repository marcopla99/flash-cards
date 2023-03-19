package com.marcopla.flashcards.presentation.carousel

import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreenState
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CarouselViewModelTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingWithEmptyGuess_thenShowError(userGuess: String) {
        val viewModel = CarouselViewModel()

        viewModel.submit(userGuess)

        assertEquals(CarouselScreenState.ERROR, viewModel.screenState.value)
    }
}