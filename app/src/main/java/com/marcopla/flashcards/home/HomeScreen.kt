package com.marcopla.flashcards.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.FlashCard

@Composable
fun HomeScreen(onNavigateToAddScreen: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddScreen
            ) {
                Icon(
                    contentDescription = stringResource(R.string.addFlashCardButtonCd),
                    imageVector = Icons.Default.Add
                )
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            ContentSection(flashCards = listOf())
        }
    }
}

@Composable
fun ContentSection(flashCards: List<FlashCard>) {
    if (flashCards.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.noFlashCardsCreated),
            textAlign = TextAlign.Center
        )
    } else {
        TODO()
    }
}