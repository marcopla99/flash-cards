package com.marcopla.flashcards.presentation.result

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.usecase.LoadResultsUseCase
import com.marcopla.flashcards.presentation.screen.result.ResultViewModel
import com.marcopla.testing_shared.FakeFlashCardDao
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ResultViewModelTest {
    @Test
    fun whenResultsAreLoaded_thenEmitResults() {
        val storedResults = listOf(
            QuizResult(FlashCard("Engels", "English"), "English", true),
            QuizResult(FlashCard("Nederlands", "Dutch"), "", false),
            QuizResult(FlashCard("Italiaans", "Italian"), "wrong", false)
        )
        val repository = FlashCardRepositoryImpl(FakeFlashCardDao()).apply {
            storedResults.forEach { addResult(it) }
        }
        val viewModel = ResultViewModel(LoadResultsUseCase(repository))

        assertEquals(storedResults, viewModel.results.value)
    }
}