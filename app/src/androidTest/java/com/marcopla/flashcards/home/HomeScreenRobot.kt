package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.FlashCard
import com.marcopla.flashcards.presentation.navigation.AppNavHost
import com.marcopla.flashcards.presentation.navigation.Routes
import com.marcopla.flashcards.presentation.screen.home.ContentSection
import org.junit.Assert.assertEquals

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchHomeScreen(
    rule: ComponentActivityTestRule,
    block: HomeScreenRobot.() -> Unit
): HomeScreenRobot {
    return HomeScreenRobot(rule).apply(block)
}

class HomeScreenRobot(
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
            navController!!.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController!!)
        }
        val addButtonContentDescription =
            rule.activity.getString(R.string.navigateToAddScreenButtonCd)
        rule.onNodeWithContentDescription(addButtonContentDescription).performClick()
    }

    infix fun verify(block: HomeScreenVerification.() -> Unit): HomeScreenVerification {
        return HomeScreenVerification(rule, navController).apply(block)
    }
}

class HomeScreenVerification(
    private val rule: ComponentActivityTestRule,
    private val navController: TestNavHostController?
) {
    fun emptyDataTextIsPresent() {
        val emptyDataText = rule.activity.getString(R.string.noFlashCardsCreated)
        rule.onNodeWithText(emptyDataText).assertIsDisplayed()
    }
    fun navigatedToAddScreen() {
        val currentRoute = navController?.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, Routes.ADD_SCREEN)
    }
}