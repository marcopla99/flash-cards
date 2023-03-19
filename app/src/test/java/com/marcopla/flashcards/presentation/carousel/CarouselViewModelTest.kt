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
        val flashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch"),
        )
        val viewModel = CarouselViewModel(flashCards)

        viewModel.submit(userGuess)

        assertEquals(CarouselScreenState.Error, viewModel.screenState.value)
    }

    @Test
    fun whenDataIsFetchedSuccessfully_thenTheStateContainsData() {
        val flashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch"),
        )
        val viewModel = CarouselViewModel(flashCards)

        viewModel.loadFlashCards()

        assertEquals(CarouselScreenState.Loaded(flashCards), viewModel.screenState.value)
    }

    @Test
    fun whenSubmittingWrongGuess_thenShowError() {
        val flashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch"),
        )
        val viewModel = CarouselViewModel(flashCards)

        viewModel.submit("wrong guess")

        assertEquals(CarouselScreenState.Error, viewModel.screenState.value)
    }

    @Test
    fun whenErrorStateIsEmitted_thenPlayNextFlashCard() {
        val flashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch"),
        )
        val viewModel = CarouselViewModel(flashCards,)

        viewModel.submit("wrong guess")

        assertEquals(FlashCard("Nederlands", "Dutch"), viewModel.currentFlashCard.value)
    }
}