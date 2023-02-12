package com.marcopla.flashcards.presentation.home

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.home.LoadFlashCardsUseCase
import com.marcopla.flashcards.presentation.screen.home.HomeScreenState
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import com.marcopla.testing_shared.TestFlashCardRepository
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
        val viewModel = HomeViewModel(LoadFlashCardsUseCase(TestFlashCardRepository()))

        assertEquals(HomeScreenState.Loading, viewModel.homeScreenState.value)
    }

    @Test
    fun homeViewModel_whenNoFlashCardsAreRetrieved_thenShowEmptyState() = runTest {
        val repository = TestFlashCardRepository()
        val viewModel = HomeViewModel(LoadFlashCardsUseCase(repository))
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.homeScreenState.collect {} }

        repository.emit(emptyList())

        assertEquals(
            HomeScreenState.Empty,
            viewModel.homeScreenState.value,
        )
        collectJob.cancel()
    }

    @Test
    fun homeViewModel_whenFetchedFlashCardsList_thenShowFlashCards() = runTest {
        val storedFlashCards = listOf(FlashCard(frontText = "Engels", backText = "English"))
        val repository = TestFlashCardRepository()
        val viewModel = HomeViewModel(LoadFlashCardsUseCase(repository))

        repository.emit(storedFlashCards)
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.homeScreenState.collect {} }

        assertEquals(HomeScreenState.Cards(storedFlashCards), viewModel.homeScreenState.value)
        collectJob.cancel()
    }
}