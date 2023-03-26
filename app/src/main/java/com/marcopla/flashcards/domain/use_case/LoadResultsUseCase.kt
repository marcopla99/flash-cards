package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject

// TODO: Currently the results are stored only in memory. Would be better to store them on disk.
class LoadResultsUseCase @Inject constructor(private val repository: FlashCardRepository) {

    operator fun invoke(): List<QuizResult> {
        return repository.getCurrentResults()
    }

    fun clearAll() {
        repository.clearResults()
    }
}
