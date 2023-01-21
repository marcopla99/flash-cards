package com.marcopla.flashcards.add

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.data_source.FakeFlashCardDao
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.add.SaveNewCardUseCase
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
    fun frontText_whenIsEmptyWhenSubmitted_thenReturnInvalidState() = runTest {
        val viewModel =
            NewFlashCardViewModel(SaveNewCardUseCase(FlashCardRepositoryImpl(FakeFlashCardDao())))
        val emptyFrontText = ""

        viewModel.attemptSubmit(emptyFrontText, ":backText:")

        assertEquals(
            FrontTextState(emptyFrontText, showError = true),
            viewModel.frontTextState.value,
        )
    }

    @Test
    fun backText_whenIsEmptyWhenSubmitted_thenReturnInvalidState() = runTest {
        val viewModel =
            NewFlashCardViewModel(SaveNewCardUseCase(FlashCardRepositoryImpl(FakeFlashCardDao())))
        val emptyBackText = ""

        viewModel.attemptSubmit(":frontText:", emptyBackText)

        assertEquals(
            BackTextState(emptyBackText, showError = true),
            viewModel.backTextState.value
        )
    }
}