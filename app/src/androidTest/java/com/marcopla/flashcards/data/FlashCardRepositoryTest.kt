package com.marcopla.flashcards.data

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

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
    fun validNewFlashCard_afterIsInserted_isPossibleToReadIt() {
        val newFlashCards = FlashCard("Engels", "English")

        repository.add(newFlashCards)

        assertEquals(true, repository.getFlashCards().contains(newFlashCards))
    }

    @Test
    fun duplicatedFlashCard_isInserted_noDuplicatesAreRead() {
        val alreadyExistentFlashCard = FlashCard("Engels", "English")
        repository.add(alreadyExistentFlashCard)

        repository.add(alreadyExistentFlashCard)

        val hasNoDuplicates = repository.getFlashCards().filter {
            it == alreadyExistentFlashCard
        }.size == 1
        assertEquals(true, hasNoDuplicates)
    }
}
