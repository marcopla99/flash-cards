package com.marcopla.flashcards.presentation.screen.home

import androidx.compose.foundation.layout.Box
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

        Surface(modifier = Modifier.padding(it)) {
            when (screenState) {
                is ScreenState.Loading -> {
                    val loadingContentDescription = stringResource(
                        id = R.string.loadingIndicator
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .semantics {
                                    contentDescription =
                                        loadingContentDescription
                                }
                        )
                    }
                }
                is ScreenState.Empty -> {
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
                is ScreenState.Cards -> {
                    val flashCards = (screenState as ScreenState.Cards).flashCards
                    LazyColumn {
                        items(flashCards.size) { index ->
                            Text(text = flashCards[index].frontText)
                        }
                    }
                }
            }
        }
    }
}