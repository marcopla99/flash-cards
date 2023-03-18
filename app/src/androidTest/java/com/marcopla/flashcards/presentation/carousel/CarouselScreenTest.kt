package com.marcopla.flashcards.presentation.carousel

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import org.junit.Rule
import org.junit.Test

class CarouselScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun dataIsLoaded_thenShowFontTextOfFirstFlashCard() {
        val flashCards = listOf(FlashCard("Engels", "English"))

        launchCarouselScreen(composeRule, flashCards) {
            // Empty
        } verify {
            frontTextIsDisplayed("Engels")
        }
    }
}
