package com.marcopla.flashcards.add

import com.marcopla.flashcards.InstantTaskExecutorExtension
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase
import com.marcopla.flashcards.presentation.screen.add.BackTextState
import com.marcopla.flashcards.presentation.screen.add.FrontTextState
import com.marcopla.flashcards.presentation.screen.add.NewFlashCardViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class NewFlashCardValidatorTest {

    @Test
    fun frontText_isEmptyWhenSubmitted_returnInvalidState() {
        val viewModel = NewFlashCardViewModel(SaveNewCardUseCase())
        val emptyFrontText = ""

        viewModel.attemptSubmit(emptyFrontText, ":backText:")

        assertEquals(
            FrontTextState(emptyFrontText, showError = true),
            viewModel.frontTextState.value,
        )
    }

    @Test
    fun backText_isEmptyWhenSubmitted_returnInvalidState() {
        val viewModel = NewFlashCardViewModel(SaveNewCardUseCase())
        val emptyBackText = ""

        viewModel.attemptSubmit(":frontText:", emptyBackText)

        assertEquals(BackTextState(emptyBackText, showError = true), viewModel.backTextState.value)
    }
}