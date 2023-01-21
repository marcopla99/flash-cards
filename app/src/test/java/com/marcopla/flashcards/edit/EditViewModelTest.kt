package com.marcopla.flashcards.edit

import com.marcopla.flashcards.R
import com.marcopla.flashcards.presentation.screen.edit.*
import com.marcopla.testing.DuplicateFlashCardRepository
import com.marcopla.testing.TestFlashCardRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EditViewModelTest {

    @Test
    fun frontText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel(EditUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit("", ":backText:")

        assertEquals(EditFrontTextState("", true), viewModel.frontTextState.value)
    }

    @Test
    fun backText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel(EditUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(":frontText:", "")

        assertEquals(EditBackTextState("", true), viewModel.backTextState.value)
    }

    @Test
    fun flashCard_whenIsEdited_andAlreadyExists_thenShowTheDuplicateError() {
        val viewModel = EditViewModel(EditUseCase(DuplicateFlashCardRepository()))

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(EditInfoState(R.string.duplicateCardError), viewModel.infoState.value)
    }
}
