package com.marcopla.flashcards.presentation.edit

import com.marcopla.flashcards.data.data_source.FakeFlashCardDao
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.add.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.add.InvalidFrontTextException
import com.marcopla.flashcards.domain.use_case.edit.EditUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
class EditUseCaseTest {
    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenFlashCardIsNotInserted(blankFrontText: String) = runTest {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao())
        val editUseCase = EditUseCase(repository)

        assertThrows(InvalidFrontTextException::class.java) {
            runBlocking { editUseCase.invoke(blankFrontText, ":backText:") }
        }

        assertEquals(emptyList<FlashCard>(), repository.getFlashCards().first())
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenFlashCardIsNotInserted(blankBackText: String) = runTest {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao())
        val editUseCase = EditUseCase(repository)

        assertThrows(InvalidBackTextException::class.java) {
            runBlocking { editUseCase.invoke(":frontText:", blankBackText) }
        }

        assertEquals(emptyList<FlashCard>(), repository.getFlashCards().first())
    }
}