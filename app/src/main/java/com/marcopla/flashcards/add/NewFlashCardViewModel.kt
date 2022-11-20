package com.marcopla.flashcards.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewFlashCardViewModel {
    private val _frontTextState = MutableLiveData<FrontTextState>()
    val frontTextState: LiveData<FrontTextState> = _frontTextState

    private val _backTextState = MutableLiveData<BackTextState>()
    val backTextState: LiveData<BackTextState> = _backTextState

    fun submit(frontText: String?, backText: String?) {
        if (frontText.isNullOrBlank() && backText is String) {
            _frontTextState.value = FrontTextState.Invalid
            return
        }
    }
}

enum class FrontTextState(val text: String = "") {
    Invalid
}

enum class BackTextState(val text: String = "") {
    Invalid
}
