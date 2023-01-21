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
import org.junit.Ignore
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
    @Ignore("fix unique constraint")
    fun validNewFlashCard_whenIsInserted_thenIsPossibleToReadIt() = runTest {
        val newFlashCards = FlashCard(frontText = "Engels", backText = "English")

        repository.add(newFlashCards)

        assertEquals(listOf(newFlashCards), repository.getFlashCards().first())
    }

    @Test
    @Ignore("fix unique constraint")
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
    fun flashCard_whenUpdatingIt_andContentDidNotChange_thenDataDoNotChange() = runTest {
        val flashCard = FlashCard(frontText = "Engels", backText = "English")
        repository.add(flashCard)
        val dataBefore = repository.getFlashCards().first()

        repository.edit(flashCard)

        val dataAfter = repository.getFlashCards().first()
        assertEquals(dataBefore, dataAfter)
    }
}
