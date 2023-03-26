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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = hiltViewModel(),
    onDoneClicked: () -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.results)) })
    }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
                .consumedWindowInsets(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(viewModel.results.value.size) { index ->
                    ResultItem(
                        quizResult = viewModel.results.value[index]
                    )
                }
            }
            val buttonText = stringResource(id = R.string.resultsDone)
            Button(
                modifier = modifier.semantics { contentDescription = buttonText },
                onClick = {
                    onDoneClicked()
                    viewModel.clearResults()
                }
            ) {
                Text(buttonText)
            }
        }
    }
}