package com.marcopla.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marcopla.flashcards.data.FlashCard
import com.marcopla.flashcards.ui.theme.FlashCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashCardsTheme {
                ContentSection(flashCards = listOf())
            }
        }
    }
}

@Composable
fun ContentSection(flashCards: List<FlashCard>) {
    if (flashCards.isEmpty()) {
        Text(text = stringResource(id = R.string.noFlashCardsCreated))
    } else {
        TODO()
    }
}