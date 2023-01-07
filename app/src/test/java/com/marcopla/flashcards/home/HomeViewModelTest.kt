package com.marcopla.flashcards.home

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.data_source.FakeFlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
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
        advanceUntilIdle()

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
        advanceUntilIdle()

        assertEquals(CardsState(listOf(FlashCard("Engels", "English"))), viewModel.cardsState.value)
    }
}

data class CardsState(val flashCards: List<FlashCard>)

data class EmptyState(@StringRes val errorStringRes: Int)

class HomeViewModel(private val loadCardsUseCase: LoadCardsUseCase) : ViewModel() {
    private val _errorState = mutableStateOf(EmptyState(-1))
    val errorState: State<EmptyState> = _errorState

    private val _cardsState = mutableStateOf(CardsState(emptyList()))
    val cardsState: State<CardsState> = _cardsState

    fun loadCards() {
        viewModelScope.launch {
            val loadedFlashCards = loadCardsUseCase.invoke()
            if (loadedFlashCards.isEmpty()) {
                _errorState.value = _errorState.value.copy(
                    errorStringRes = R.string.noFlashCardsCreated
                )
            } else {
                _cardsState.value = _cardsState.value.copy(flashCards = loadedFlashCards)
            }
        }
    }
}

class LoadCardsUseCase(private val repository: FlashCardRepository) {
    suspend fun invoke(): List<FlashCard> {
        return repository.getFlashCards()
    }
}
