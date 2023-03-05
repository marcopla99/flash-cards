package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.MainDispatcherExtension
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException
import com.marcopla.testing_shared.FakeFlashCardDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class AddUseCaseTest {

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun frontText_whenIsBlank_thenThrowException(blankFrontText: String) = runTest {
        val addUseCase = AddUseCase(FlashCardRepositoryImpl(FakeFlashCardDao()))

        assertThrows(
            InvalidFrontTextException::class.java
        ) {
            runBlocking {
                addUseCase.invoke(blankFrontText, ":backText:")
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  "])
    fun backText_whenIsBlank_thenThrowException(blankBackText: String) = runTest {
        val addUseCase = AddUseCase(FlashCardRepositoryImpl(FakeFlashCardDao()))

        assertThrows(
            InvalidBackTextException::class.java,
        ) {
            runBlocking {
                addUseCase.invoke(":frontText:", blankBackText)
            }
        }
    }
}