package com.marcopla.flashcards.presentation.screen.home

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadCardsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val loadCardsUseCase: LoadCardsUseCase) : ViewModel() {
    private val _errorState = mutableStateOf(EmptyState(-1))
    val errorState: State<EmptyState> = _errorState

    private val _cardsState = mutableStateOf(CardsState(emptyList()))
    val cardsState: State<CardsState> = _cardsState

    init {
        loadCards()
    }

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

data class CardsState(val flashCards: List<FlashCard>)
data class EmptyState(@StringRes val errorStringRes: Int)