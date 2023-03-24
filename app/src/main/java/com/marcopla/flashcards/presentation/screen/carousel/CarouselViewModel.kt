package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CarouselViewModel(
    private val flashCards: List<FlashCard>,
    private val loadUseCase: LoadUseCase
) : ViewModel() {
    private var fetchedFlashCards: List<FlashCard> = emptyList()

    private var currentFlashCardIndex = 0

    private val _screenState = mutableStateOf<CarouselScreenState>(CarouselScreenState.Empty)
    val screenState: State<CarouselScreenState> = _screenState

    fun submit(userGuess: String) {
        val isNotCorrect = userGuess != fetchedFlashCards[currentFlashCardIndex].backText
        if (isNotCorrect) {
            _screenState.value =
                CarouselScreenState.Error(fetchedFlashCards[currentFlashCardIndex])
        } else {
            _screenState.value =
                CarouselScreenState.Success(fetchedFlashCards[currentFlashCardIndex])
        }

        if (fetchedFlashCards[currentFlashCardIndex] != flashCards.last()) {
            currentFlashCardIndex += 1
            if (isNotCorrect) {
                _screenState.value =
                    CarouselScreenState.Error(fetchedFlashCards[currentFlashCardIndex])
            } else {
                _screenState.value =
                    CarouselScreenState.Success(fetchedFlashCards[currentFlashCardIndex])
            }
        } else {
            _screenState.value = CarouselScreenState.Finished
        }
    }

    fun loadFlashCards() {
        viewModelScope.launch {
            fetchedFlashCards = loadUseCase.loadAll().first()
            _screenState.value = CarouselScreenState.Loaded(fetchedFlashCards[0])
        }
    }
}

sealed interface CarouselScreenState {
    data class Loaded(val flashCard: FlashCard) : CarouselScreenState
    object Empty : CarouselScreenState
    data class Error(val nextFlashCard: FlashCard) : CarouselScreenState
    data class Success(val nextFlashCard: FlashCard) : CarouselScreenState
    object Finished : CarouselScreenState
}