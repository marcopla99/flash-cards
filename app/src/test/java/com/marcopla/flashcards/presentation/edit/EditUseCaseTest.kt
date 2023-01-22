package com.marcopla.flashcards.presentation.edit

import com.marcopla.flashcards.domain.use_case.add.InvalidFrontTextException
import com.marcopla.flashcards.domain.use_case.edit.EditUseCase
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EditUseCaseTest {

    @Test
    fun frontText_whenIsEmpty_thenThrowException() = runTest {
        val useCase = EditUseCase(TestFlashCardRepository())

        assertThrows(InvalidFrontTextException::class.java) {
            runBlocking { useCase.invoke("", ":backText:") }
        }
    }
}