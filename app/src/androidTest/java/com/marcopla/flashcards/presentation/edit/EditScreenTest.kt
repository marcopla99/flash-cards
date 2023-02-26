package com.marcopla.flashcards.presentation.edit

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.testing_shared.DuplicateFlashCardRepository
import org.junit.Rule
import org.junit.Test

class EditScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun editingFlashCard_whenIsAlreadyStored_thenShowDuplicateError() {
        val selectedFlashCard = FlashCard("Engels", "wrong").apply { id = 0 }

        launchEditScreenFor(selectedFlashCard, DuplicateFlashCardRepository(), composeRule) {
            // FIXME: This makes the test composable recomposing infinitely. https://stackoverflow.com/questions/75571664/updating-textfield-with-data-class-as-state-throws-composenotidleexception-in-ui
//            editBackText("English")
            submit()
        } verify {
            duplicateErrorIsDisplayed()
        }
    }
}
