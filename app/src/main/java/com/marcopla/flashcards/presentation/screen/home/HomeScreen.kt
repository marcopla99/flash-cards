package com.marcopla.flashcards.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAddScreen: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddScreen
            ) {
                Icon(
                    contentDescription = stringResource(R.string.navigateToAddScreenButtonCd),
                    imageVector = Icons.Default.Add
                )
            }
        }
    ) {
        val screenState: ScreenState by viewModel.screenState.collectAsStateWithLifecycle()
        ScreenContent(it, screenState)
    }
}

@Composable
private fun ScreenContent(
    paddingValues: PaddingValues,
    screenState: ScreenState,
) {
    Surface(modifier = Modifier.padding(paddingValues)) {
        when (screenState) {
            is ScreenState.Loading -> LoadingIndicator()
            is ScreenState.Empty -> EmptyMessage()
            is ScreenState.Cards -> CardsList(screenState.flashCards)
        }
    }
}

@Composable
private fun LoadingIndicator() {
    val loadingContentDescription = stringResource(
        id = R.string.loadingIndicator
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics { contentDescription = loadingContentDescription }
        )
    }
}

@Composable
private fun EmptyMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.noFlashCardsCreated),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CardsList(flashCards: List<FlashCard>) {
    LazyColumn {
        items(flashCards.size) { index ->
            val itemContentDescription =
                stringResource(R.string.flashCardItem, flashCards[index].frontText)
            Box(
                modifier = Modifier.semantics {
                    contentDescription = itemContentDescription
                }
            ) {
                Text(
                    text = flashCards[index].frontText,
                )
            }
        }
    }
}
