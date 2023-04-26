package com.marcopla.flashcards.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeRoute(
    onNavigateToAddScreen: () -> Unit,
    onItemClicked: (Int) -> Unit,
    onNavigateToCarouselScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val screenState: HomeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    HomeScreen(
        screenState = screenState,
        onNavigateToAddScreen = onNavigateToAddScreen,
        onItemClicked = onItemClicked,
        onNavigateToCarouselScreen = onNavigateToCarouselScreen
    )
}

@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    onNavigateToAddScreen: () -> Unit,
    onItemClicked: (Int) -> Unit,
    onNavigateToCarouselScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.homeScreenTitle)) },
                actions = {
                    if (screenState is HomeScreenState.Cards) {
                        Icon(
                            modifier = Modifier.clickable {
                                onNavigateToCarouselScreen()
                            },
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = stringResource(
                                id = R.string.carouselButton
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddScreen
            ) {
                Icon(
                    contentDescription = stringResource(R.string.navigateToAddScreenButton),
                    imageVector = Icons.Default.Add
                )
            }
        }
    ) { padding ->
        ScreenContent(
            screenState,
            modifier = modifier.padding(padding),
            onItemClicked = onItemClicked
        )
    }
}

@Composable
private fun ScreenContent(
    screenState: HomeScreenState,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {
    when (screenState) {
        is HomeScreenState.Loading -> LoadingIndicator(modifier = modifier)
        is HomeScreenState.Empty -> EmptyMessage(modifier = modifier)
        is HomeScreenState.Cards -> CardsList(
            screenState.flashCards.toImmutableList(),
            modifier = modifier,
            onItemClicked = onItemClicked
        )
    }
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val loadingContentDescription = stringResource(
        id = R.string.loadingIndicator
    )
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics { contentDescription = loadingContentDescription }
        )
    }
}

@Composable
private fun EmptyMessage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.noFlashCardsCreated),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CardsList(
    flashCards: ImmutableList<FlashCard>,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(flashCards.size) { index ->
            val itemContentDescription =
                stringResource(R.string.flashCardItem, flashCards[index].frontText)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .semantics {
                        contentDescription = itemContentDescription
                    },
                elevation = 4.dp,
                onClick = {
                    onItemClicked(flashCards[index].id)
                }
            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = flashCards[index].frontText,
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = flashCards[index].backText,
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
