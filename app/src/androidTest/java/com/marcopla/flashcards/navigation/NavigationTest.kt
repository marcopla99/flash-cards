package com.marcopla.flashcards.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.MainActivity
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
    fun homeScreen_whenIsOpen_andAfterClickingTheAddButton_thenGoToAddScreen() {
        launchApp(composeRule) {
            clickAddButton()
        } verify {
            addScreenIsOpen()
        }
    }
}
