package com.marcopla.flashcards.domain.usecase

import com.marcopla.flashcards.data.model.QuizResult
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

// TODO: Currently the results are stored only in memory. Would be better to store them on disk.
class LoadResultsUseCase @Inject constructor(private val repository: FlashCardRepository) {

    operator fun invoke(): Flow<List<QuizResult>> {
        return repository.getCurrentResults()
    }

    fun clearAll() {
        repository.clearResults()
    }
}
