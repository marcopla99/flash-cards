package com.marcopla.flashcards.presentation.carousel

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreenState
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CarouselViewModelTest {

    private val loadedFlashCards: List<FlashCard> = listOf(
        FlashCard("Engels", "English"),
        FlashCard("Nederlands", "Dutch"),
    )

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingWithEmptyGuess_thenShowError(userGuess: String) {
        val viewModel = CarouselViewModel(loadedFlashCards)

        viewModel.submit(userGuess)

        assertEquals(CarouselScreenState.Error, viewModel.screenState.value)
    }

    @Test
    fun whenDataIsFetchedSuccessfully_thenTheStateContainsData() {
        val viewModel = CarouselViewModel(loadedFlashCards)

        viewModel.loadFlashCards()

        assertEquals(CarouselScreenState.Loaded(loadedFlashCards), viewModel.screenState.value)
    }

    @Test
    fun whenSubmittingWrongGuess_thenShowError() {
        val viewModel = CarouselViewModel(loadedFlashCards)

        viewModel.submit("wrong guess")

        assertEquals(CarouselScreenState.Error, viewModel.screenState.value)
    }

    @Test
    fun whenErrorStateIsEmitted_thenPlayNextFlashCard() {
        val viewModel = CarouselViewModel(loadedFlashCards)

        viewModel.submit("wrong guess")

        assertEquals(FlashCard("Nederlands", "Dutch"), viewModel.currentFlashCard.value)
    }

    @Test
    fun whenSubmittingCorrectGuess_thenShowSuccess() {
        val viewModel = CarouselViewModel(loadedFlashCards)

        viewModel.submit("English")

        assertEquals(CarouselScreenState.Success, viewModel.screenState.value)
    }
}