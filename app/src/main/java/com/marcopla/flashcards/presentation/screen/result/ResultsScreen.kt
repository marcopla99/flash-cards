package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.QuizResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ResultsRoute(
    onDoneClicked: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    ResultsScreen(
        onDoneClicked = onDoneClicked,
        results = viewModel.results.value.toImmutableList(),
        clearResults = { viewModel.clearResults() }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultsScreen(
    onDoneClicked: () -> Unit,
    results: ImmutableList<QuizResult>,
    clearResults: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.results)) })
    }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
                .consumeWindowInsets(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(results.size) { index ->
                    ResultItem(
                        quizResult = results[index]
                    )
                }
            }
            val buttonText = stringResource(id = R.string.resultsDone)
            Button(
                modifier = Modifier.semantics { contentDescription = buttonText },
                onClick = {
                    onDoneClicked()
                    clearResults()
                }
            ) {
                Text(buttonText)
            }
        }
    }
}
