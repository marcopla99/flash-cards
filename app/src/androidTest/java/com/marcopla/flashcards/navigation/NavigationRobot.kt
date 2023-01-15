package com.marcopla.flashcards.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.MainActivity
import com.marcopla.flashcards.R

typealias ComposeTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchApp(
    composeRule: ComposeTestRule,
    block: NavigationRobot.() -> Unit,
): NavigationRobot {
    return NavigationRobot(composeRule).apply(block)
}

class NavigationRobot(
    private val composeRule: ComposeTestRule,
) {
    fun clickAddButton() {
        val addButtonContentDescription = composeRule.activity.getString(
            R.string.navigateToAddScreenButton
        )
        composeRule.onNodeWithContentDescription(addButtonContentDescription).performClick()
    }

    infix fun verify(block: NavigationVerification.() -> Unit): NavigationVerification {
        return NavigationVerification(composeRule).apply(block)
    }
}

class NavigationVerification(
    private val composeRule: ComposeTestRule,
) {
    fun addScreenIsOpen() {
        val addScreenTitle = composeRule.activity.getString(R.string.addScreenTitle)
        composeRule.onNodeWithText(addScreenTitle).assertIsDisplayed()
    }
}