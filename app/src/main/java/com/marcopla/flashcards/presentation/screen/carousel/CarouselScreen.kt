package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarouselScreen(
    modifier: Modifier = Modifier,
    viewModel: CarouselViewModel = hiltViewModel(),
    flashCards: List<FlashCard>,
    onLastFlashCardPlayed: () -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.loadFlashCards()
    }

    var currentFlashCardIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.carouselScreenTitle))
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (flashCards.isEmpty() || currentFlashCardIndex == flashCards.size - 1) {
                    onLastFlashCardPlayed()
                } else {
                    currentFlashCardIndex += 1
                }
            }) {
                Icon(imageVector = Icons.Default.Done, stringResource(id = R.string.nextButton))
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(4.dp)
                .consumedWindowInsets(it)
        ) {
            val solution = flashCards.getOrNull(currentFlashCardIndex)?.frontText ?: ""
            Text(text = solution)

            var backTextValue by remember { mutableStateOf("") }
            TextField(
                value = backTextValue,
                onValueChange = { newValue -> backTextValue = newValue }
            )
        }
    }
}