package com.marcopla.flashcards.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EditViewModelTest {

    @Test
    fun frontText_whenIsEmpty_thenShowError() {
        val viewModel = EditViewModel()

        viewModel.attemptSubmit("", ":backText:")

        assertEquals(EditFrontTextState("", true), viewModel.frontTextState.value)
    }
}

class EditViewModel {
    private val _frontTextState = mutableStateOf(EditFrontTextState())
    val frontTextState: State<EditFrontTextState> = _frontTextState

    fun attemptSubmit(frontText: String, backText: String) {
        _frontTextState.value = _frontTextState.value.copy(showError = true)
    }
}

data class EditFrontTextState(
    val value: String = "",
    val showError: Boolean = false,
)
