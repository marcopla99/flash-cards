package com.marcopla.flashcards.presentation.edit

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.R
import com.marcopla.flashcards.domain.use_case.edit.EditFlashCardUseCase
import com.marcopla.flashcards.presentation.screen.edit.*
import com.marcopla.testing.DuplicateFlashCardRepository
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class EditViewModelTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenShowError(blankFrontText: String) {
        val viewModel = EditViewModel(EditFlashCardUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(blankFrontText, ":backText:")

        assertEquals(EditFrontTextState("", true), viewModel.frontTextState.value)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenShowError(blankBackText: String) = runTest {
        val viewModel = EditViewModel(EditFlashCardUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(":frontText:", blankBackText)

        assertEquals(EditBackTextState("", true), viewModel.backTextState.value)
    }

    @Test
    fun flashCard_whenIsEdited_andAlreadyExists_thenShowTheDuplicateError() = runTest {
        val viewModel = EditViewModel(EditFlashCardUseCase(DuplicateFlashCardRepository()))

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(
            EditScreenState.Error(R.string.duplicateCardError),
            viewModel.screenState.value,
        )
    }

    @Test
    fun flashCard_whenEditedSuccessfully_thenReturnSuccess() = runTest {
        val viewModel = EditViewModel(EditFlashCardUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(EditScreenState.Success, viewModel.screenState.value)
    }
}