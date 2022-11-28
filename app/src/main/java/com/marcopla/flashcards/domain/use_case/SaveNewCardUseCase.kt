package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.FlashCard

class SaveNewCardUseCase {
    @Throws(InvalidBackException::class, InvalidFrontException::class)
    operator fun invoke(frontText: String?, backText: String?) {
        if (frontText.isNullOrBlank()) {
            throw InvalidFrontException()
        }
        if (backText.isNullOrBlank()) {
            throw InvalidBackException()
        }
        val card = FlashCard(frontText, backText)
        storeCard(card)
    }

    private fun storeCard(card: FlashCard) {
    }
}

class InvalidFrontException : IllegalStateException()

class InvalidBackException : IllegalStateException()
