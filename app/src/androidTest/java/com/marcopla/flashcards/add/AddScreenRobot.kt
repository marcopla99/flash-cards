package com.marcopla.flashcards.add

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.MainActivity
import com.marcopla.flashcards.R

typealias MainActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchAddScreen(
    rule: MainActivityTestRule,
    block: AddScreenRobot.() -> Unit
): AddScreenRobot {
    return AddScreenRobot(rule).apply(block)
}

class AddScreenRobot(private val rule: MainActivityTestRule) {
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

class AddScreenVerification(private val rule: MainActivityTestRule) {
    fun addedCardToastIsDisplayed() {
        rule.onNodeWithText(rule.activity.getString(R.string.cardAdded)).assertIsDisplayed()
    }
}
