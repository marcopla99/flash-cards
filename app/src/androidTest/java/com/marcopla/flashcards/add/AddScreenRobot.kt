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
    composeRule: ComponentActivityTestRule,
    repository: FlashCardRepository,
    block: AddScreenRobot.() -> Unit
): AddScreenRobot {
    composeRule.setContent {
        AddScreen(NewFlashCardViewModel(SaveNewCardUseCase(repository)))
    }
    return AddScreenRobot(composeRule).apply(block)
}

class AddScreenRobot(private val composeRule: ComponentActivityTestRule) {
    fun typeTextFront(frontText: String) {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.frontTextFieldCd))
            .performTextInput(frontText)
    }

    fun typeTextBack(backText: String) {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.backTextFieldCd))
            .performTextInput(backText)
    }

    fun submit() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.addCardButtonCd))
            .performClick()
    }

    infix fun verify(block: AddScreenVerification.() -> Unit): AddScreenVerification {
        return AddScreenVerification(composeRule).apply(block)
    }
}

class AddScreenVerification(private val composeRule: ComponentActivityTestRule) {
    fun addedCardSnackbarIsDisplayed() {
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.cardAdded))
            .assertIsDisplayed()
    }

    fun duplicateErrorMessageIsDisplayed() {
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.duplicateCardError))
            .assertIsDisplayed()
    }
}
