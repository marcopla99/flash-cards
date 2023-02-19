package com.marcopla.flashcards.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.MainActivity
import com.marcopla.flashcards.data.model.FlashCard
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
    fun homeScreen_whenClickingTheAddButton_thenGoToAddScreen() {
        launchApp(composeRule) {
            clickAddButton()
        } verify {
            addScreenIsOpen()
        }
    }

    @Test
    fun homeScreen_whenClickingFlashCard_thenGoToEditScreen() {
        val flashCard = FlashCard(frontText = "Engels", backText = "English")

        launchApp(composeRule) {
            addNewFlashCard(flashCard)
            clickOnFlashCard(flashCard)
        } verify {
            editScreenIsOpen()
        }
    }

    @Test
    fun homeScreen_whenClickingFlashCard_thenEditScreenShowsCorrectData() {
        val flashCard = FlashCard(frontText = "Engels", backText = "English")

        launchApp(composeRule) {
            addNewFlashCard(flashCard)
            clickOnFlashCard(flashCard)
        } verify {
            editScreenDisplaysFlashCard(flashCard)
        }
    }

    @Test
    fun editScreen_whenClickingEditButtonSuccessfully_thenGoToHomeScreen() {
        val flashCardWithWrongBackText = FlashCard("Engels", "wrong")

        launchApp(composeRule) {
            addNewFlashCard(flashCardWithWrongBackText)
            clickOnFlashCard(flashCardWithWrongBackText)
            editBackText("English")
            clickEditButton()
        } verify {
            homeScreenIsOpen()
        }
    }
}
