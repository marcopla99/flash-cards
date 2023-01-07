package com.marcopla.flashcards.home

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.data_source.FakeFlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.LoadCardsUseCase
import com.marcopla.flashcards.presentation.screen.home.CardsState
import com.marcopla.flashcards.presentation.screen.home.EmptyState
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class HomeViewModelTest {

    @Test
    fun cardsList_isEmpty_returnNoCardsState() = runTest {
        val viewModel = HomeViewModel(LoadCardsUseCase(FlashCardRepository(FakeFlashCardDao())))

        viewModel.loadCards()

        assertEquals(EmptyState(R.string.noFlashCardsCreated), viewModel.errorState.value)
    }

    @Test
    fun cardsList_hasData_returnStateWithFlashCards() = runTest {
        val viewModel = HomeViewModel(
            LoadCardsUseCase(
                FlashCardRepository(
                    FakeFlashCardDao(
                        mutableListOf(
                            FlashCard(
                                "Engels",
                                "English"
                            ),
                        ),
                    ),
                ),
            ),
        )

        viewModel.loadCards()

        assertEquals(CardsState(listOf(FlashCard("Engels", "English"))), viewModel.cardsState.value)
    }
}