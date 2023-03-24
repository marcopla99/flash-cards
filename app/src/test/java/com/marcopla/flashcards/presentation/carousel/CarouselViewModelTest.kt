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

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun whenSubmittingWithEmptyGuess_thenShowErrorWithNextFlashCardToPlay(emptyUserGuess: String) {
        val viewModel = CarouselViewModel(
            LoadUseCase(
                FlashCardRepositoryImpl(
                    FakeFlashCardDao(
                        listOf(
                            FlashCard("Engels", "English"),
                            FlashCard("Nederlands", "Dutch"),
                        )
                    )
                )
            )
        )
        viewModel.loadFlashCards()

        viewModel.submit(emptyUserGuess)

        assertEquals(
            CarouselScreenState.Wrong(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenDataIsFetchedSuccessfully_thenTheStateContainsData() {
        val viewModel = CarouselViewModel(
            LoadUseCase(
                FlashCardRepositoryImpl(
                    FakeFlashCardDao(
                        listOf(
                            FlashCard("Engels", "English"),
                            FlashCard("Nederlands", "Dutch"),
                        )
                    )
                )
            )
        )

        viewModel.loadFlashCards()

        assertEquals(
            CarouselScreenState.Initial(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch"),
                )[0]
            ),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenSubmittingWrongGuess_thenShowErrorWithNextFlashCardToPlay() {
        val viewModel = CarouselViewModel(
            LoadUseCase(
                FlashCardRepositoryImpl(
                    FakeFlashCardDao(
                        listOf(
                            FlashCard("Engels", "English"),
                            FlashCard("Nederlands", "Dutch"),
                        )
                    )
                )
            )
        )
        viewModel.loadFlashCards()

        viewModel.submit("wrong guess")

        assertEquals(
            CarouselScreenState.Wrong(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenSubmittingCorrectGuess_thenShowSuccessWithNextFlashCardToPlay() {
        val viewModel = CarouselViewModel(
            LoadUseCase(
                FlashCardRepositoryImpl(
                    FakeFlashCardDao(
                        listOf(
                            FlashCard("Engels", "English"),
                            FlashCard("Nederlands", "Dutch"),
                        )
                    )
                )
            )
        )
        viewModel.loadFlashCards()

        viewModel.submit("English")

        assertEquals(
            CarouselScreenState.Correct(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenLastFlashCardIsPlayed_thenEmitFinishedState() {
        val viewModel = CarouselViewModel(
            LoadUseCase(
                FlashCardRepositoryImpl(
                    FakeFlashCardDao(
                        listOf(
                            FlashCard("Engels", "English"),
                            FlashCard("Nederlands", "Dutch"),
                        )
                    )
                )
            )
        )
        viewModel.loadFlashCards()
        viewModel.submit("English")

        viewModel.submit("Dutch")

        assertEquals(CarouselScreenState.Finished, viewModel.screenState.value)
    }
}