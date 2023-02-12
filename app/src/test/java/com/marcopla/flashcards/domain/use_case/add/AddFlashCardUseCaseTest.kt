package com.marcopla.flashcards.domain.use_case.add

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.presentation.screen.add.AddViewModel
import com.marcopla.flashcards.presentation.screen.add.BackTextState
import com.marcopla.flashcards.presentation.screen.add.FrontTextState
import com.marcopla.testing_shared.FakeFlashCardDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class AddFlashCardUseCaseTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenReturnInvalidState(blankFrontText: String) = runTest {
        val viewModel =
            AddViewModel(AddFlashCardUseCase(FlashCardRepositoryImpl(FakeFlashCardDao())))

        viewModel.attemptSubmit(blankFrontText, ":backText:")

        Assertions.assertEquals(
            FrontTextState("", showError = true),
            viewModel.frontTextState.value,
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenReturnInvalidState(blankBackText: String) = runTest {
        val viewModel =
            AddViewModel(AddFlashCardUseCase(FlashCardRepositoryImpl(FakeFlashCardDao())))

        viewModel.attemptSubmit(":frontText:", blankBackText)

        Assertions.assertEquals(
            BackTextState("", showError = true),
            viewModel.backTextState.value
        )
    }
}