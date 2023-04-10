package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarouselScreen(
    modifier: Modifier = Modifier,
    viewModel: CarouselViewModel = hiltViewModel(),
    onLastFlashCardPlayed: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadFlashCards()
    }

    val screenState = viewModel.screenState
    if (screenState.value == CarouselScreenState.Finished) {
        LaunchedEffect(key1 = Unit) {
            onLastFlashCardPlayed()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.carouselScreenTitle))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.submit(viewModel.guessInput.value)
            }) {
                Icon(imageVector = Icons.Default.Done, stringResource(id = R.string.nextButton))
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(4.dp)
                .consumeWindowInsets(it)
        ) {
            Prompt(screenState.value)
            Guess(
                modifier = modifier,
                value = viewModel.guessInput.value,
                onValueChange = viewModel::updateGuessInput
            )
        }
    }
}

@Composable
private fun Guess(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    val guessFieldContentDescription = stringResource(id = R.string.guessField)
    TextField(
        modifier = modifier.semantics {
            contentDescription = guessFieldContentDescription
        },
        value = value,
        onValueChange = onValueChange
    )
}

@Composable
private fun Prompt(screenState: CarouselScreenState) {
    val promptText = when (screenState) {
        is CarouselScreenState.Correct -> {
            screenState.nextFlashCard.frontText
        }
        is CarouselScreenState.Initial -> {
            screenState.flashCard.frontText
        }
        is CarouselScreenState.Wrong -> {
            screenState.nextFlashCard.frontText
        }
        else -> {
            ""
        }
    }
    Text(text = promptText)
}