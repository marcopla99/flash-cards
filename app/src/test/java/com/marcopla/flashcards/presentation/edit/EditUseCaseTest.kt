package com.marcopla.flashcards.presentation.edit

import com.marcopla.flashcards.domain.use_case.add.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.add.InvalidFrontTextException
import com.marcopla.flashcards.domain.use_case.edit.EditUseCase
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
class EditUseCaseTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenThrowException(blankFrontText: String) = runTest {
        val editUseCase = EditUseCase(TestFlashCardRepository())

        assertThrows(InvalidFrontTextException::class.java) {
            runBlocking { editUseCase.invoke(blankFrontText, ":backText:") }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenThrowException(blankBackText: String) = runTest {
        val editUseCase = EditUseCase(TestFlashCardRepository())

        assertThrows(InvalidBackTextException::class.java) {
            runBlocking { editUseCase.invoke(":frontText:", blankBackText) }
        }
    }
}