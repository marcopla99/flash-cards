package com.marcopla.flashcards.presentation.screen.home

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadCardsUseCase: LoadCardsUseCase,
) : ViewModel() {
    private val _errorState = mutableStateOf(EmptyState(-1))
    val errorState: State<EmptyState> = _errorState

    val cardsState: StateFlow<CardsState> = loadCardsStateStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CardsState(emptyList()),
    )

    private fun loadCardsStateStream(): Flow<CardsState> {
        return loadCardsUseCase.invoke().map { CardsState(it) }
    }
}

data class CardsState(val flashCards: List<FlashCard>)
data class EmptyState(@StringRes val errorStringRes: Int)