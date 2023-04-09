package com.marcopla.flashcards.presentation.add

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.R
import com.marcopla.flashcards.domain.usecase.AddUseCase
import com.marcopla.flashcards.presentation.screen.add.*
import com.marcopla.testing_shared.DuplicateFlashCardRepository
import com.marcopla.testing_shared.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class AddViewModelTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenShowError(blankFrontText: String) {
        val viewModel = AddViewModel(AddUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(blankFrontText, ":backText:")

        assertEquals(FrontTextState(showError = true), viewModel.frontTextState.value)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenShowError(blankBackText: String) {
        val viewModel = AddViewModel(AddUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(":frontText:", blankBackText)

        assertEquals(BackTextState(showError = true), viewModel.backTextState.value)
    }

    @Test
    fun flashCard_whenIsAdded_andAlreadyExists_thenShowDuplicateError() {
        val viewModel = AddViewModel(AddUseCase(DuplicateFlashCardRepository()))

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(InfoTextState(R.string.duplicateCardError), viewModel.infoTextState.value)
    }

    @Test
    fun flashCard_whenIsAddedSuccessfully_thenReturnSuccess() {
        val viewModel = AddViewModel(AddUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit("Nederlands", "Dutch")

        assertEquals(AddScreenState.SUCCESSFUL_SAVE, viewModel.addScreenState.value)
    }
}