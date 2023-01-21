package com.marcopla.flashcards.edit

import com.marcopla.flashcards.presentation.screen.edit.EditBackTextState
import com.marcopla.flashcards.presentation.screen.edit.EditFrontTextState
import com.marcopla.flashcards.presentation.screen.edit.EditViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EditViewModelTest {

    @Test
    fun frontText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel()

        viewModel.attemptSubmit("", ":backText:")

        assertEquals(EditFrontTextState("", true), viewModel.frontTextState.value)
    }

    @Test
    fun backText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel()

        viewModel.attemptSubmit(":frontText:", "")

        assertEquals(EditBackTextState("", true), viewModel.backTextState.value)
    }
}