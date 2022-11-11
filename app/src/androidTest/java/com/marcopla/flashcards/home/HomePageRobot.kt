package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.ContentSection
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.FlashCard

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchHomePage(
    rule: ComponentActivityTestRule,
    block: HomePageRobot.() -> Unit
): HomePageRobot {
    return HomePageRobot(rule).apply(block)
}

class HomePageRobot(
    private val rule: ComponentActivityTestRule
) {
    fun setFlashCards(flashCards: List<FlashCard>) {
        rule.setContent {
            ContentSection(
                flashCards = flashCards
            )
        }
    }

    infix fun verify(block: HomePageVerification.() -> Unit): HomePageVerification {
        return HomePageVerification(rule).apply(block)
    }
}

class HomePageVerification(
    private val rule: ComponentActivityTestRule
) {
    fun emptyDataTextIsPresent() {
        val emptyDataText = rule.activity.getString(R.string.noFlashCardsCreated)
        rule.onNodeWithText(emptyDataText).assertIsDisplayed()
    }
}
