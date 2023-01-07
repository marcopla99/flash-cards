package com.marcopla.flashcards.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.MainActivity
import com.marcopla.flashcards.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_isOpen_andAfterClickingTheAddButton_goToAddScreen() {
        launchApp(composeRule) {
            clickAddButton()
        } verify {
            addScreenIsOpen()
        }
    }
}

private fun launchApp(
    composeRule: ComposeTestRule,
    block: NavigationRobot.() -> Unit,
): NavigationRobot {
    return NavigationRobot(composeRule).apply(block)
}

typealias ComposeTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

class NavigationRobot(
    private val composeRule: ComposeTestRule,
) {
    fun clickAddButton() {
        val addButtonContentDescription = composeRule.activity.getString(
            R.string.navigateToAddScreenButtonCd
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
