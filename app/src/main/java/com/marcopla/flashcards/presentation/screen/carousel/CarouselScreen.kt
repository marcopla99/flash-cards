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

@Composable
fun CarouselRoute(
    viewModel: CarouselViewModel = hiltViewModel(),
    onLastFlashCardPlayed: () -> Unit
) {
    val screenState = viewModel.screenState.value
    if (screenState == CarouselScreenState.Finished) {
        LaunchedEffect(key1 = Unit) {
            onLastFlashCardPlayed()
        }
    }

    CarouselScreen(
        screenState = screenState,
        onSubmitClicked = {
            viewModel.submit(viewModel.guessInput.value)
        },
        guessInputState = viewModel.guessInput.value,
        onGuessInputChange = viewModel::updateGuessInput
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarouselScreen(
    screenState: CarouselScreenState,
    onSubmitClicked: () -> Unit,
    guessInputState: String,
    onGuessInputChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.carouselScreenTitle))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSubmitClicked) {
                Icon(imageVector = Icons.Default.Done, stringResource(id = R.string.nextButton))
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(4.dp)
                .consumeWindowInsets(it)
        ) {
            Prompt(screenState)
            Guess(
                value = guessInputState,
                onValueChange = onGuessInputChange
            )
        }
    }
}

@Composable
private fun Guess(
    value: String,
    modifier: Modifier = Modifier,
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
