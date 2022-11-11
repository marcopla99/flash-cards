package com.marcopla.flashcards.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class HomePageTest {

    @get:Rule
    val homePageTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homePage_emptyData_showEmptyMessage() {
        launchHomePage(homePageTestRule) {
            setFlashCards(listOf())
        } verify {
            emptyDataTextIsPresent()
        }
    }
}