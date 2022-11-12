package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.ContentSection
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.FlashCard
import com.marcopla.flashcards.navigation.AppNavHost
import org.junit.Assert.assertEquals

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
    private var navController: TestNavHostController? = null

    fun setFlashCards(flashCards: List<FlashCard>) {
        rule.setContent {
            ContentSection(
                flashCards = flashCards
            )
        }
    }

    fun clickAddButton() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            AppNavHost(navController = navController!!)
        }
        val addButtonContentDescription = rule.activity.getString(R.string.addFlashCardButtonCd)
        rule.onNodeWithContentDescription(addButtonContentDescription).performClick()
    }

    infix fun verify(block: HomePageVerification.() -> Unit): HomePageVerification {
        return HomePageVerification(rule, navController).apply(block)
    }
}

class HomePageVerification(
    private val rule: ComponentActivityTestRule,
    private val navController: TestNavHostController?
) {
    fun emptyDataTextIsPresent() {
        val emptyDataText = rule.activity.getString(R.string.noFlashCardsCreated)
        rule.onNodeWithText(emptyDataText).assertIsDisplayed()
    }
    fun navigatedToAddPage() {
        val currentRoute = navController?.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, ADD_PAGE_ROUTE)
    }
}

private const val ADD_PAGE_ROUTE = "Add Flash Card"
