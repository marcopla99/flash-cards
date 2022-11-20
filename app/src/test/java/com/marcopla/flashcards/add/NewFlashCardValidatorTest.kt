package com.marcopla.flashcards.add

import com.marcopla.flashcards.InstantTaskExecutorExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class NewFlashCardValidatorTest {

    @Test
    fun frontText_isEmptyWhenSubmitted_returnInvalidState() {
        val viewModel = NewFlashCardViewModel()

        viewModel.submit("", ":backText:")

        assertEquals(FrontTextState.Invalid, viewModel.frontTextState.value)
    }
}