package com.marcopla.flashcards.add

import com.marcopla.flashcards.InstantTaskExecutorExtension
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase
import com.marcopla.flashcards.presentation.screen.add.BackTextState
import com.marcopla.flashcards.presentation.screen.add.FrontTextState
import com.marcopla.flashcards.presentation.screen.add.NewCardState
import com.marcopla.flashcards.presentation.screen.add.NewFlashCardViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class NewFlashCardValidatorTest {

    @Test
    fun frontText_isEmptyWhenSubmitted_returnInvalidState() {
        val viewModel = NewFlashCardViewModel(SaveNewCardUseCase())

        viewModel.attemptSubmit("", ":backText:")

        assertEquals(FrontTextState.Invalid, viewModel.frontTextState.value)
    }

    @Test
    fun backText_isEmptyWhenSubmitted_returnInvalidState() {
        val viewModel = NewFlashCardViewModel(SaveNewCardUseCase())

        viewModel.attemptSubmit(":frontText:", "")

        assertEquals(BackTextState.Invalid, viewModel.backTextState.value)
    }

    @Test
    fun frontText_and_backText_areValid_returnValidCardState() {
        val viewModel = NewFlashCardViewModel(SaveNewCardUseCase())

        viewModel.attemptSubmit("English", "Engels")

        assertEquals(NewCardState.Valid, viewModel.newCardState.value)
    }
}