package com.marcopla.flashcards.add

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.data_source.FakeFlashCardDao
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase
import com.marcopla.flashcards.presentation.screen.add.BackTextState
import com.marcopla.flashcards.presentation.screen.add.FrontTextState
import com.marcopla.flashcards.presentation.screen.add.NewFlashCardViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class NewFlashCardValidatorTest {

    @Test
    fun frontText_isEmptyWhenSubmitted_returnInvalidState() = runTest {
        val viewModel =
            NewFlashCardViewModel(SaveNewCardUseCase(FlashCardRepository(FakeFlashCardDao())))
        val emptyFrontText = ""

        viewModel.attemptSubmit(emptyFrontText, ":backText:")

        assertEquals(
            FrontTextState(emptyFrontText, showError = true),
            viewModel.frontTextState.value,
        )
    }

    @Test
    fun backText_isEmptyWhenSubmitted_returnInvalidState() = runTest {
        val viewModel =
            NewFlashCardViewModel(SaveNewCardUseCase(FlashCardRepository(FakeFlashCardDao())))
        val emptyBackText = ""

        viewModel.attemptSubmit(":frontText:", emptyBackText)

        assertEquals(
            BackTextState(emptyBackText, showError = true),
            viewModel.backTextState.value
        )
    }
}