package com.marcopla.flashcards.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.SaveNewCardUseCase
import com.marcopla.flashcards.presentation.screen.add.AddScreen
import com.marcopla.flashcards.presentation.screen.add.NewFlashCardViewModel

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchAddScreen(
    rule: ComponentActivityTestRule,
    repository: FlashCardRepository, // FIXME: pass the viewmodel instead
    block: AddScreenRobot.() -> Unit
): AddScreenRobot {
    rule.setContent {
        AddScreen(NewFlashCardViewModel(SaveNewCardUseCase(repository)))
    }
    return AddScreenRobot(rule).apply(block)
}

class AddScreenRobot(private val rule: ComponentActivityTestRule) {
    fun typeTextFront(frontText: String) {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.frontTextFieldCd))
            .performTextInput(frontText)
    }

    fun typeTextBack(backText: String) {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.backTextFieldCd))
            .performTextInput(backText)
    }

    fun submit() {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.addCardButtonCd))
            .performClick()
    }

    infix fun verify(block: AddScreenVerification.() -> Unit): AddScreenVerification {
        return AddScreenVerification(rule).apply(block)
    }
}

class AddScreenVerification(private val rule: ComponentActivityTestRule) {
    fun addedCardSnackbarIsDisplayed() {
        rule.onNodeWithText(rule.activity.getString(R.string.cardAdded)).assertIsDisplayed()
    }

    fun duplicateErrorMessageIsDisplayed() {
        rule.onNodeWithText(rule.activity.getString(R.string.duplicateCardError))
            .assertIsDisplayed()
    }
}
