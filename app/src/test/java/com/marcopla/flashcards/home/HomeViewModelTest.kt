package com.marcopla.flashcards.home

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.marcopla.flashcards.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HomeViewModelTest {

    @Test
    fun cardsList_isEmpty_returnNoCardsState() {
        val viewModel = HomeViewModel()

        viewModel.loadCards()

        assertEquals(EmptyState(R.string.noFlashCardsCreated), viewModel.errorState.value)
    }
}

data class EmptyState(@StringRes val errorStringRes: Int)

class HomeViewModel {
    private val _errorState = mutableStateOf(EmptyState(-1))
    val errorState: State<EmptyState> = _errorState

    fun loadCards() {
        _errorState.value = _errorState.value.copy(errorStringRes = R.string.noFlashCardsCreated)
    }
}
