package com.marcopla.flashcards.presentation.add

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.domain.use_case.add.AddFlashCardsUseCase
import com.marcopla.flashcards.presentation.screen.add.AddViewModel
import com.marcopla.flashcards.presentation.screen.add.BackTextState
import com.marcopla.flashcards.presentation.screen.add.FrontTextState
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class AddViewModelTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenShowError(blankFrontText: String) {
        val viewModel = AddViewModel(AddFlashCardsUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(blankFrontText, ":backText:")

        assertEquals(FrontTextState(showError = true), viewModel.frontTextState.value)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenShowError(blankBackText: String) {
        val viewModel = AddViewModel(AddFlashCardsUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(":frontText:", blankBackText)

        assertEquals(BackTextState(showError = true), viewModel.backTextState.value)
    }
}