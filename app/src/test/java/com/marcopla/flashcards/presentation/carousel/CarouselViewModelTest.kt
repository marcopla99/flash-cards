package com.marcopla.flashcards.presentation.carousel

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.usecase.LoadUseCase
import com.marcopla.flashcards.domain.usecase.SubmitQuizUseCase
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreenState
import com.marcopla.flashcards.presentation.screen.carousel.CarouselViewModel
import com.marcopla.testing_shared.FakeFlashCardDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )
            )
        )
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )

        viewModel.submit(emptyUserGuess)

        assertEquals(
            CarouselScreenState.Wrong(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenDataIsFetchedSuccessfully_thenTheStateContainsData() {
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )
            )
        )
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )

        assertEquals(
            CarouselScreenState.Initial(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )[0]
            ),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenSubmittingWrongGuess_thenShowErrorWithNextFlashCardToPlay() {
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )
            )
        )
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )

        viewModel.submit("wrong guess")

        assertEquals(
            CarouselScreenState.Wrong(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenSubmittingCorrectGuess_thenShowSuccessWithNextFlashCardToPlay() {
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )
            )
        )
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )

        viewModel.submit("English")

        assertEquals(
            CarouselScreenState.Correct(FlashCard("Nederlands", "Dutch")),
            viewModel.screenState.value
        )
    }

    @Test
    fun whenLastFlashCardIsPlayed_thenEmitFinishedState() {
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )
            )
        )
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )
        viewModel.submit("English")

        viewModel.submit("Dutch")

        assertEquals(CarouselScreenState.Finished, viewModel.screenState.value)
    }

    @Test
    fun whenSubmitting_thenClearGuessField() {
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(
                    FlashCard("Engels", "English"),
                    FlashCard("Nederlands", "Dutch")
                )
            )
        )
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )
        viewModel.updateGuessInput(":guess:")

        viewModel.submit(":guess:")

        assertEquals("", viewModel.guessInput.value)
    }

    @Test
    fun whenFinished_allFlashCardsWereProcessed() = runTest {
        val storedFlashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch"),
            FlashCard("Italiaans", "Italian")
        )
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao(storedFlashCards))
        val viewModel = CarouselViewModel(
            LoadUseCase(repository),
            SubmitQuizUseCase(repository)
        )

        repeat(storedFlashCards.size) {
            viewModel.submit(":guess:")
        }

        assertEquals(storedFlashCards.size, repository.getCurrentResults().first().size)
    }
}