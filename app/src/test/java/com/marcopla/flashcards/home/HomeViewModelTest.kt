package com.marcopla.flashcards.home

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadCardsUseCase
import com.marcopla.flashcards.presentation.screen.home.CardsState
import com.marcopla.flashcards.presentation.screen.home.EmptyState
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class HomeViewModelTest {

    @Test
    fun homeViewModel_isCreated_and_noFlashCardsAreRetrieved_emitEmptyFlashCardsState() = runTest {
        val initialData = emptyList<FlashCard>()

        val viewModel = HomeViewModel(LoadCardsUseCase(TestFlashCardRepository(initialData)))

        assertEquals(EmptyState(R.string.noFlashCardsCreated), viewModel.errorState.value)
    }

    @Test
    fun homeViewModel_isCreated_and_fetchedListOfFlashCards_returnStateWithFlashCards() = runTest {
        val storedFlashCards = listOf(FlashCard("Engels", "English"))

        val viewModel = HomeViewModel(LoadCardsUseCase(TestFlashCardRepository(storedFlashCards)))

        assertEquals(CardsState(storedFlashCards), viewModel.cardsState.value)
    }
}