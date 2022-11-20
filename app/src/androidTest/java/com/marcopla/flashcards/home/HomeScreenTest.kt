package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val homeScreenTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_emptyData_showEmptyMessage() {
        launchHomeScreen(homeScreenTestRule) {
            setFlashCards(listOf())
        } verify {
            emptyDataTextIsPresent()
        }
    }

    @Test
    fun homeScreen__navigateToAddScreen() {
        launchHomeScreen(homeScreenTestRule) {
            clickAddButton()
        } verify {
            navigatedToAddScreen()
        }
    }
}