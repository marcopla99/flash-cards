package com.marcopla.flashcards.presentation.edit

import androidx.lifecycle.SavedStateHandle
import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.DeleteUseCase
import com.marcopla.flashcards.domain.use_case.EditFlashCardUseCase
import com.marcopla.flashcards.domain.use_case.LoadFlashCardsUseCase
import com.marcopla.flashcards.presentation.navigation.FLASH_CARD_ID_ARG_KEY
import com.marcopla.flashcards.presentation.screen.edit.*
import com.marcopla.testing_shared.DuplicateFlashCardRepository
import com.marcopla.testing_shared.FakeFlashCardDao
import com.marcopla.testing_shared.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class EditViewModelTest {

    private val selectedFlashCard: FlashCard = FlashCard("Engels", "English").apply { id = 0 }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenShowError(blankFrontText: String) {
        val viewModel = EditViewModel(
            SavedStateHandle(mapOf(FLASH_CARD_ID_ARG_KEY to selectedFlashCard.id)),
            EditFlashCardUseCase(TestFlashCardRepository()),
            LoadFlashCardsUseCase(
                FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard)))
            ),
            DeleteUseCase(TestFlashCardRepository()),
        )

        viewModel.attemptSubmit(blankFrontText, selectedFlashCard.backText)

        assertTrue(viewModel.frontTextState.value.showError)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenShowError(blankBackText: String) = runTest {
        val viewModel = EditViewModel(
            SavedStateHandle(mapOf(FLASH_CARD_ID_ARG_KEY to selectedFlashCard.id)),
            EditFlashCardUseCase(TestFlashCardRepository()),
            LoadFlashCardsUseCase(
                FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard))),
            ),
            DeleteUseCase(TestFlashCardRepository()),
        )

        viewModel.attemptSubmit(selectedFlashCard.frontText, blankBackText)

        assertTrue(viewModel.backTextState.value.showError)
    }

    @Test
    fun flashCard_whenIsEdited_andAlreadyExists_thenShowTheDuplicateError() = runTest {
        val viewModel = EditViewModel(
            SavedStateHandle(mapOf(FLASH_CARD_ID_ARG_KEY to selectedFlashCard.id)),
            EditFlashCardUseCase(DuplicateFlashCardRepository()),
            LoadFlashCardsUseCase(
                FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard)))
            ),
            DeleteUseCase(TestFlashCardRepository()),
        )

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(
            EditScreenState.Error(R.string.duplicateCardError),
            viewModel.screenState.value,
        )
    }

    @Test
    fun flashCard_whenEditedSuccessfully_thenReturnSuccess() = runTest {
        val viewModel = EditViewModel(
            SavedStateHandle(mapOf(FLASH_CARD_ID_ARG_KEY to selectedFlashCard.id)),
            EditFlashCardUseCase(TestFlashCardRepository()),
            LoadFlashCardsUseCase(
                FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard)))
            ),
            DeleteUseCase(TestFlashCardRepository()),
        )

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(EditScreenState.Success, viewModel.screenState.value)
    }

    @Test
    fun selectedFlashCard_whenDeleted_thenReturnDeletedState() {
        val viewModel = EditViewModel(
            SavedStateHandle(mapOf(FLASH_CARD_ID_ARG_KEY to selectedFlashCard.id)),
            EditFlashCardUseCase(TestFlashCardRepository()),
            LoadFlashCardsUseCase(
                FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard)))
            ),
            DeleteUseCase(FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard))))
        )

        viewModel.delete()

        assertEquals(EditScreenState.Deleted, viewModel.screenState.value)
    }
}