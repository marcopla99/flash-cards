package com.marcopla.flashcards.data

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class FlashCardRepositoryTest {

    @Inject
    lateinit var repository: FlashCardRepository

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun validNewFlashCard_whenIsInserted_thenIsPossibleToReadIt() = runTest {
        val newFlashCards = FlashCard(frontText = "Engels", backText = "English")

        repository.add(newFlashCards)

        assertEquals(listOf(newFlashCards), repository.getFlashCards().first())
    }

    @Test
    fun duplicatedFlashCard_whenIsInserted_thenNoDuplicatesAreRead() = runTest {
        val alreadyExistentFlashCard = FlashCard(frontText = "Engels", backText = "English")
        repository.add(alreadyExistentFlashCard)

        assertThrows(DuplicateInsertionException::class.java) {
            runBlocking { repository.add(alreadyExistentFlashCard) }
        }

        val hasNoDuplicates = repository.getFlashCards().first().filter {
            it == alreadyExistentFlashCard
        }.size == 1
        assertTrue(hasNoDuplicates)
    }

    @Test
    fun flashCard_whenUpdatingIt_andContentDidNotChange_thenDataDoesNotChange() = runTest {
        val flashCard = FlashCard(frontText = "Engels", backText = "English")
        repository.add(flashCard)
        val dataBefore = repository.getFlashCards().first()

        repository.edit(flashCard)

        val dataAfter = repository.getFlashCards().first()
        assertEquals(dataBefore, dataAfter)
    }

    @Test
    fun flashCard_whenUpdatingIt_butAlreadyExists_thenNoDuplicatesAreRead() = runTest {
        val alreadyExistentFlashCard = FlashCard(frontText = "Engels", backText = "English")
        val flashCardToEdit = FlashCard(frontText = "Nederlands", backText = "Dutch")
        repository.add(alreadyExistentFlashCard, flashCardToEdit)
        val flashCardToEditId = repository.getFlashCards().first().first {
            it == flashCardToEdit
        }.id

        val editedFlashCard = alreadyExistentFlashCard.copy().apply {
            id = flashCardToEditId
        }
        assertThrows(DuplicateInsertionException::class.java) {
            runBlocking { repository.edit(editedFlashCard) }
        }

        val hasNoDuplicates = repository.getFlashCards().first().filter {
            it == alreadyExistentFlashCard
        }.size == 1
        assertTrue(hasNoDuplicates)
    }

    @Test
    fun flashCard_whenUpdatingItSuccessfully_thenIsPossibleToReadIt() = runTest {
        repository.add(FlashCard(frontText = "Engels", backText = "English"))
        val flashCardId = repository.getFlashCards().first()[0].id

        val updatedFlashCard = FlashCard(frontText = "Nederlands", backText = "Dutch").apply {
            id = flashCardId
        }
        repository.edit(updatedFlashCard)

        assertEquals(
            listOf(FlashCard(frontText = "Nederlands", backText = "Dutch")),
            repository.getFlashCards().first()
        )
    }

    @Test
    fun existingFlashCard_whenDelete_thenIsNotFetchedAnymore() = runTest {
        val flashCard = FlashCard("Engels", "English")
        repository.add(flashCard)
        if (!repository.getFlashCards().first().contains(flashCard)) {
            fail("Insertion failed!")
        }
        val flashCardWithGeneratedId = repository.getFlashCards().first()[0]

        repository.deleteById(flashCardWithGeneratedId.id)

        val fetchedFlashCards = repository.getFlashCards().first()
        assertEquals(emptyList<FlashCard>(), fetchedFlashCards)
    }
}
