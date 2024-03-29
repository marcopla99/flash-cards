package com.marcopla.flashcards.domain.usecase

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing_shared.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadUseCaseTest {

    @Test
    fun whenInvoked_thenReturnStoredFlashCards() = runTest {
        val repository = TestFlashCardRepository()
        val useCase = LoadUseCase(repository)
        val storedFlashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch")
        )

        repository.emit(storedFlashCards)

        assertEquals(storedFlashCards, useCase.loadAll().first())
    }
}