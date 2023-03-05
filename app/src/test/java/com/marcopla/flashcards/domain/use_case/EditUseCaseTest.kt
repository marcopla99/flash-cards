package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
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
class EditUseCaseTest {
    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenFlashCardIsNotInserted(blankFrontText: String) = runTest {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao())
        val editUseCase = EditUseCase(repository)

        assertThrows(InvalidFrontTextException::class.java) {
            runBlocking { editUseCase.invoke(blankFrontText, ":backText:", 0) }
        }

        assertEquals(emptyList<FlashCard>(), repository.getFlashCards().first())
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenFlashCardIsNotInserted(blankBackText: String) = runTest {
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao())
        val editUseCase = EditUseCase(repository)

        assertThrows(InvalidBackTextException::class.java) {
            runBlocking { editUseCase.invoke(":frontText:", blankBackText, 0) }
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
        val editUseCase = EditUseCase(repository)

        val editedFlashCard = FlashCard(frontText = "Nederlands", backText = "Dutch").apply {
            id = 1
        }
        editUseCase.invoke(editedFlashCard.frontText, editedFlashCard.backText, editedFlashCard.id)

        assertEquals(
            listOf(editedFlashCard, otherFlashCard),
            repository.getFlashCards().first()
        )
    }
}