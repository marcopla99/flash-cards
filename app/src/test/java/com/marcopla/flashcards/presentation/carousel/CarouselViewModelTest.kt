package com.marcopla.flashcards.presentation.carousel

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.LoadUseCase
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreenState
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import com.marcopla.testing_shared.FakeFlashCardDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class CarouselViewModelTest {

    private val loadedFlashCards: List<FlashCard> = listOf(
        FlashCard("Engels", "English"),
        FlashCard("Nederlands", "Dutch"),
    )

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingWithEmptyGuess_thenShowErrorWithNextFlashCardToPlay(userGuess: String) {
        val viewModel = CarouselViewModel(
            loadedFlashCards,
            LoadUseCase(FlashCardRepositoryImpl(FakeFlashCardDao(loadedFlashCards)))
        )
        viewModel.loadFlashCards()

        viewModel.submit(userGuess)

        assertEquals(
            CarouselScreenState.Error(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenDataIsFetchedSuccessfully_thenTheStateContainsData() {
        val viewModel = CarouselViewModel(
            loadedFlashCards,
            LoadUseCase(FlashCardRepositoryImpl(FakeFlashCardDao(loadedFlashCards)))
        )
        viewModel.loadFlashCards()

        viewModel.loadFlashCards()

        assertEquals(CarouselScreenState.Loaded(loadedFlashCards[0]), viewModel.screenState.value)
    }

    @Test
    fun whenSubmittingWrongGuess_thenShowErrorWithNextFlashCardToPlay() {
        val viewModel = CarouselViewModel(
            loadedFlashCards,
            LoadUseCase(FlashCardRepositoryImpl(FakeFlashCardDao(loadedFlashCards)))
        )
        viewModel.loadFlashCards()

        viewModel.submit("wrong guess")

        assertEquals(
            CarouselScreenState.Error(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenSubmittingCorrectGuess_thenShowSuccessWithNextFlashCardToPlay() {
        val viewModel = CarouselViewModel(
            loadedFlashCards,
            LoadUseCase(FlashCardRepositoryImpl(FakeFlashCardDao(loadedFlashCards)))
        )
        viewModel.loadFlashCards()

        viewModel.submit("English")

        assertEquals(
            CarouselScreenState.Success(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenLastFlashCardIsPlayed_thenEmitFinishedState() {
        val viewModel = CarouselViewModel(
            loadedFlashCards,
            LoadUseCase(FlashCardRepositoryImpl(FakeFlashCardDao(loadedFlashCards)))
        )
        viewModel.loadFlashCards()
        viewModel.submit("English")

        viewModel.submit("Dutch")

        assertEquals(CarouselScreenState.Finished, viewModel.screenState.value)
    }
}