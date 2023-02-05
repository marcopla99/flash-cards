package com.marcopla.flashcards.domain.use_case.home

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing.TestFlashCardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadFlashCardsUseCaseTest {

    @Test
    fun whenInvoked_thenReturnStoredFlashCards() = runTest {
        val repository = TestFlashCardRepository()
        val useCase = LoadFlashCardsUseCase(repository)
        val storedFlashCards = listOf(
            FlashCard("Engels", "English"),
            FlashCard("Nederlands", "Dutch")
        )

        repository.emit(storedFlashCards)

        assertEquals(storedFlashCards, useCase.invoke().first())
    }
}