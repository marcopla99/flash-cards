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

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class EditViewModelTest {

    @Test
    fun frontText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel(EditFlashCardUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit("", ":backText:")

        assertEquals(EditFrontTextState("", true), viewModel.frontTextState.value)
    }

    @Test
    fun backText_whenIsEmpty_thenShowError() = runTest {
        val viewModel = EditViewModel(EditFlashCardUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(":frontText:", "")

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