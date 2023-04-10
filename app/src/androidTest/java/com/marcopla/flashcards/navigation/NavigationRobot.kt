package com.marcopla.flashcards.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.MainActivity
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard

typealias ComposeTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchApp(
    composeRule: ComposeTestRule,
    block: NavigationRobot.() -> Unit
): NavigationRobot {
    return NavigationRobot(composeRule).apply(block)
}

class NavigationRobot(
    private val composeRule: ComposeTestRule
) {
    fun clickAddButton() {
        val addButtonContentDescription = composeRule.activity.getString(
            R.string.navigateToAddScreenButton
        )
        composeRule.onNodeWithContentDescription(addButtonContentDescription).performClick()
    }

    fun addNewFlashCard(flashCard: FlashCard) {
        clickAddButton()

        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.frontTextField))
            .performTextInput(flashCard.frontText)
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.backTextField))
            .performTextInput(flashCard.backText)
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.addCardButton))
            .performClick()

        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    infix fun verify(block: NavigationVerification.() -> Unit): NavigationVerification {
        return NavigationVerification(composeRule).apply(block)
    }

    fun clickOnFlashCard(flashCard: FlashCard) {
        val flashCardContentDescription = composeRule.activity.getString(
            R.string.flashCardItem,
            flashCard.frontText
        )

        composeRule
            .onNodeWithContentDescription(flashCardContentDescription)
            .performClick()
    }

    fun editBackText(newText: String) {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.backTextField))
            .performTextInput(newText)
    }

    fun clickEditButton() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.editButton))
            .performClick()
    }

    fun delete() {
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.deleteButton)
            )
            .performClick()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.ok)).performClick()
    }

    fun clickCarouselButton() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.carouselButton))
            .performClick()
    }

    fun clickNextButton() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.nextButton))
            .performClick()
    }

    fun clickDoneButton() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.resultsDone))
            .performClick()
    }
}

class NavigationVerification(
    private val composeRule: ComposeTestRule
) {
    fun addScreenIsOpen() {
        val addScreenTitle = composeRule.activity.getString(R.string.addScreenTitle)
        composeRule.onNodeWithText(addScreenTitle).assertIsDisplayed()
    }

    fun editScreenIsOpen() {
        val editScreenTitle = composeRule.activity.getString(R.string.editScreenTitle)
        composeRule.onNodeWithText(editScreenTitle).assertIsDisplayed()
    }

    fun editScreenDisplaysFlashCard(flashCard: FlashCard) {
        composeRule.onNodeWithText(flashCard.frontText).assertIsDisplayed()
        composeRule.onNodeWithText(flashCard.backText).assertIsDisplayed()
    }

    fun homeScreenIsOpen() {
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.homeScreenTitle))
            .assertIsDisplayed()
    }

    fun carouselScreenIsOpen() {
        composeRule
            .onNodeWithText(
                composeRule.activity.getString(R.string.carouselScreenTitle)
            )
            .assertIsDisplayed()
    }

    fun resultScreenIsOpen() {
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.results))
            .assertIsDisplayed()
    }
}