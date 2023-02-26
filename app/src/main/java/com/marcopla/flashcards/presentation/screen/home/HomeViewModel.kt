package com.marcopla.flashcards.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadFlashCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadFlashCardsUseCase: LoadFlashCardsUseCase,
) : ViewModel() {
    val homeScreenState: StateFlow<HomeScreenState> = loadCardsStateStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenState.Loading,
    )

    private fun loadCardsStateStream(): Flow<HomeScreenState> {
        return loadFlashCardsUseCase.loadAll().map {
            if (it.isEmpty()) {
                HomeScreenState.Empty
            } else {
                HomeScreenState.Cards(it)
            }
        }
    }
}

sealed interface HomeScreenState {
    data class Cards(val flashCards: List<FlashCard>) : HomeScreenState
    object Empty : HomeScreenState
    object Loading : HomeScreenState
}
