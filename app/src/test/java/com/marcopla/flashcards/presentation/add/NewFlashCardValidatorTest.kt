package com.marcopla.flashcards.presentation.add

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
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class NewFlashCardValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenReturnInvalidState(blankFrontText: String) = runTest {
        val viewModel =
            NewFlashCardViewModel(SaveNewCardUseCase(FlashCardRepositoryImpl(FakeFlashCardDao())))

        viewModel.attemptSubmit(blankFrontText, ":backText:")

        assertEquals(
            FrontTextState("", showError = true),
            viewModel.frontTextState.value,
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenReturnInvalidState(blankBackText: String) = runTest {
        val viewModel =
            NewFlashCardViewModel(SaveNewCardUseCase(FlashCardRepositoryImpl(FakeFlashCardDao())))

        viewModel.attemptSubmit(":frontText:", blankBackText)

        assertEquals(
            BackTextState("", showError = true),
            viewModel.backTextState.value
        )
    }
}