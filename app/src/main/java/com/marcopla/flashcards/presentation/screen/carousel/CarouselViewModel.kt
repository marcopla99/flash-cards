package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.domain.use_case.LoadUseCase
import com.marcopla.flashcards.domain.use_case.SubmitQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class CarouselViewModel @Inject constructor(
    private val loadUseCase: LoadUseCase,
    private val submitQuiz: SubmitQuizUseCase
) : ViewModel() {
    private var flashCards: List<FlashCard> = emptyList()

    private var currentFlashCardIndex = 0

    private val _guessInput = mutableStateOf("")
    val guessInput: State<String> = _guessInput

    private val _screenState = mutableStateOf<CarouselScreenState>(CarouselScreenState.Empty)
    val screenState: State<CarouselScreenState> = _screenState

    private val isLastFlashCard: Boolean
        get() = currentFlashCardIndex == flashCards.size - 1

    fun submit(userGuess: String) {
        _guessInput.value = ""
        if (isLastFlashCard) {
            _screenState.value = CarouselScreenState.Finished
            return
        }
        if (submitQuiz.invoke(flashCards[currentFlashCardIndex], userGuess)) {
            currentFlashCardIndex += 1
            _screenState.value =
                CarouselScreenState.Correct(flashCards[currentFlashCardIndex])
        } else {
            currentFlashCardIndex += 1
            _screenState.value =
                CarouselScreenState.Wrong(flashCards[currentFlashCardIndex])
        }
    }

    fun loadFlashCards() {
        viewModelScope.launch {
            flashCards = loadUseCase.loadAll().first()
            _screenState.value = CarouselScreenState.Initial(flashCards[0])
        }
    }

    fun updateGuessInput(input: String) {
        _guessInput.value = input
    }
}

sealed interface CarouselScreenState {
    data class Initial(val flashCard: FlashCard) : CarouselScreenState
    data class Wrong(val nextFlashCard: FlashCard) : CarouselScreenState
    data class Correct(val nextFlashCard: FlashCard) : CarouselScreenState
    object Empty : CarouselScreenState
    object Finished : CarouselScreenState
}