package com.marcopla.flashcards.presentation.edit

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import com.marcopla.flashcards.domain.use_case.EditFlashCardUseCase
import com.marcopla.flashcards.domain.use_case.LoadFlashCardsUseCase
import com.marcopla.flashcards.presentation.navigation.FLASH_CARD_ID_ARG_KEY
import com.marcopla.flashcards.presentation.screen.edit.EditScreen
import com.marcopla.flashcards.presentation.screen.edit.EditViewModel
import com.marcopla.testing_shared.DuplicateFlashCardRepository
import com.marcopla.testing_shared.FakeFlashCardDao
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

typealias ComponentActivityTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

fun launchEditScreenFor(
    selectedFlashCard: FlashCard,
    flashCardRepository: FlashCardRepository,
    composeRule: ComponentActivityTestRule,
    block: EditScreenRobot.() -> Unit,
): EditScreenRobot {
    composeRule.setContent {
        EditScreen(
            viewModel = EditViewModel(
                SavedStateHandle(mapOf(FLASH_CARD_ID_ARG_KEY to selectedFlashCard.id)),
                EditFlashCardUseCase(flashCardRepository),
                LoadFlashCardsUseCase(
                    FlashCardRepositoryImpl(FakeFlashCardDao(listOf(selectedFlashCard)))
                ),
            )
        ) {}
    }
    return EditScreenRobot(composeRule).apply(block)
}

class EditScreenRobot(private val composeRule: ComponentActivityTestRule) {
    fun editBackText(newText: String) {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.backTextField))
            .performTextInput(newText)
    }

    fun submit() {
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.editButton))
            .performClick()
    }

    infix fun verify(block: EditScreenVerification.() -> Unit): EditScreenVerification {
        return EditScreenVerification(composeRule).apply(block)
    }
}

class EditScreenVerification(private val composeRule: ComponentActivityTestRule) {
    fun duplicateErrorIsDisplayed() {
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.duplicateCardError))
            .assertIsDisplayed()
    }
}
