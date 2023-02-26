package com.marcopla.flashcards.domain.use_case.edit

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.EditFlashCardUseCase
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException
import com.marcopla.testing_shared.FakeFlashCardDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
class EditFlashCardUseCaseTest {
    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenFlashCardIsNotInserted(blankFrontText: String) = runTest {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao())
        val editFlashCardUseCase = EditFlashCardUseCase(repository)

        assertThrows(InvalidFrontTextException::class.java) {
            runBlocking { editFlashCardUseCase.invoke(FlashCard(blankFrontText, ":backText:")) }
        }

        assertEquals(emptyList<FlashCard>(), repository.getFlashCards().first())
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenFlashCardIsNotInserted(blankBackText: String) = runTest {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao())
        val editFlashCardUseCase = EditFlashCardUseCase(repository)

        assertThrows(InvalidBackTextException::class.java) {
            runBlocking { editFlashCardUseCase.invoke(FlashCard(":frontText:", blankBackText)) }
        }

        assertEquals(emptyList<FlashCard>(), repository.getFlashCards().first())
    }

    @Test
    fun flashCard_whenIsValid_thenDataIsUpdated() = runTest {
        val flashCardToEdit = FlashCard(frontText = "Engels", backText = "English").apply {
            id = 1
        }
        val otherFlashCard = FlashCard(frontText = "Italiaans", backText = "Italian").apply {
            id = 2
        }
        val repository = FlashCardRepositoryImpl(
            FakeFlashCardDao(
                listOf(flashCardToEdit, otherFlashCard)
            )
        )
        val editFlashCardUseCase = EditFlashCardUseCase(repository)

        val editedFlashCard = FlashCard(frontText = "Nederlands", backText = "Dutch").apply {
            id = 1
        }
        editFlashCardUseCase.invoke(editedFlashCard)

        assertEquals(
            listOf(editedFlashCard, otherFlashCard),
            repository.getFlashCards().first()
        )
    }
}