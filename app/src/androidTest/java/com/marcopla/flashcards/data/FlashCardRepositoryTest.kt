package com.marcopla.flashcards.data

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
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
    fun validNewFlashCard_afterIsInserted_isPossibleToReadIt() = runTest {
        val newFlashCards = FlashCard("Engels", "English")

        repository.add(newFlashCards)

        assertEquals(true, repository.getFlashCards().contains(newFlashCards))
    }

    @Test
    fun duplicatedFlashCard_isInserted_noDuplicatesAreRead() = runTest {
        val alreadyExistentFlashCard = FlashCard("Engels", "English")
        repository.add(alreadyExistentFlashCard)

        assertThrows(DuplicateInsertionException::class.java) {
            runBlocking {
                repository.add(alreadyExistentFlashCard)
            }
        }

        val hasNoDuplicates = repository.getFlashCards().filter {
            it == alreadyExistentFlashCard
        }.size == 1
        assertEquals(true, hasNoDuplicates)
    }
}
