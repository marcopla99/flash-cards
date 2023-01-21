package com.marcopla.flashcards.edit

import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.presentation.screen.edit.*
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.flow.Flow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EditViewModelTest {

    @Test
    fun frontText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel(EditUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit("", ":backText:")

        assertEquals(EditFrontTextState("", true), viewModel.frontTextState.value)
    }

    @Test
    fun backText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel(EditUseCase(TestFlashCardRepository()))

        viewModel.attemptSubmit(":frontText:", "")

        assertEquals(EditBackTextState("", true), viewModel.backTextState.value)
    }

    @Test
    fun flashCard_whenIsEdited_andAlreadyExists_thenShowTheDuplicateError() {
        val viewModel = EditViewModel(EditUseCase(DuplicateFlashCardRepository()))

        viewModel.attemptSubmit("Engels", "English")

        assertEquals(EditInfoState(R.string.duplicateCardError), viewModel.infoState.value)
    }
}

class DuplicateFlashCardRepository : FlashCardRepository {
    override fun getFlashCards(): Flow<List<FlashCard>> {
        TODO("Not yet implemented")
    }

    override suspend fun add(newFlashCard: FlashCard) {
        throw DuplicateInsertionException()
    }
}