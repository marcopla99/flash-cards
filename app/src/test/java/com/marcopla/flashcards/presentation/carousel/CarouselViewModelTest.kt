package com.marcopla.flashcards.presentation.carousel

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreenState
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CarouselViewModelTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingWithEmptyGuess_thenShowError(userGuess: String) {
        val viewModel = CarouselViewModel(emptyList())

        viewModel.submit(userGuess)

        assertEquals(CarouselScreenState.Error, viewModel.screenState.value)
    }

    @Test
    fun whenDataIsFetchedSuccessfully_thenTheStateContainsData() {
        val flashCards = listOf(FlashCard("Engels", "English"))
        val viewModel = CarouselViewModel(flashCards)

        viewModel.loadFlashCards()

        assertEquals(CarouselScreenState.Loaded(flashCards), viewModel.screenState.value)
    }

    @Test
    fun whenSubmittingWrongGuess_thenShowError() {
        val flashCard = FlashCard("Engels", "English")
        val viewModel = CarouselViewModel(listOf(flashCard))

        viewModel.submit("wrong guess")

        assertEquals(CarouselScreenState.Error, viewModel.screenState.value)
    }
}