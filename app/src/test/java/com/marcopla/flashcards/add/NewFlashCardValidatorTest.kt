package com.marcopla.flashcards.add

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NewFlashCardValidatorTest {

    @Test
    fun frontText_isEmptyWhenSubmitted_returnInvalidState() {
        val viewModel = NewFlashCardViewModel()

        viewModel.submit("", ":backText:")

        assertEquals(FrontTextState.Invalid, viewModel.frontTextState.value)
    }
}