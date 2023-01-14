package com.marcopla.flashcards.home

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadCardsUseCase
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import com.marcopla.flashcards.presentation.screen.home.ScreenState
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.internal.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class HomeViewModelTest {

    @Test
    fun homeViewModel_whenIsCreated_thenShowLoading() = runTest {
        val viewModel = HomeViewModel(LoadCardsUseCase(TestFlashCardRepository()))
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.screenState.collect {} }

        assertEquals(ScreenState.Loading, viewModel.screenState.value)
        collectJob.cancel()
    }

    @Test
    fun homeViewModel_whenNoFlashCardsAreRetrieved_showEmptyState() = runTest {
        val repository = TestFlashCardRepository()
        val viewModel = HomeViewModel(LoadCardsUseCase(repository))
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.screenState.collect {} }

        repository.emit(emptyList())

        assertEquals(
            ScreenState.Empty,
            viewModel.screenState.value,
        )
        collectJob.cancel()
    }

    @Test
    fun homeViewModel_whenFetchedListOfFlashCards_thenShowFlashCards() = runTest {
        val storedFlashCards = listOf(FlashCard("Engels", "English"))
        val repository = TestFlashCardRepository()
        val viewModel = HomeViewModel(LoadCardsUseCase(repository))
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.screenState.collect {} }

        repository.emit(storedFlashCards)

        assertEquals(ScreenState.Cards(storedFlashCards), viewModel.screenState.value)
        collectJob.cancel()
    }
}