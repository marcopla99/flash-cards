package com.marcopla.flashcards.presentation.screen.home

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
    val screenState: StateFlow<ScreenState> = loadCardsStateStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ScreenState.Loading,
    )

    private fun loadCardsStateStream(): Flow<ScreenState> {
        return loadCardsUseCase.invoke().map {
            if (it.isEmpty()) {
                ScreenState.Empty
            } else {
                ScreenState.Cards(it)
            }
        }
    }
}

sealed interface ScreenState {
    data class Cards(val flashCards: List<FlashCard>) : ScreenState
    object Empty : ScreenState
    object Loading : ScreenState
}
