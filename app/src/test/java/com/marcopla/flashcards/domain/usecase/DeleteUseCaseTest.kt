package com.marcopla.flashcards.domain.usecase

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.testing_shared.FakeFlashCardDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteUseCaseTest {
    @Test
    fun singleFlashCardStored_whenDeleteUseCaseIsInvoked_thenRetrieveEmptyData() = runTest {
        val flashCardId = 0
        val flashCard = FlashCard("Engels", "English").apply {
            id = flashCardId
        }
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao(listOf(flashCard)))
        val deleteUseCase = DeleteUseCase(repository)

        deleteUseCase.invoke(flashCardId)

        val fetchedFlashCards = repository.getFlashCards().first()
        assertEquals(emptyList<FlashCard>(), fetchedFlashCards)
    }
}