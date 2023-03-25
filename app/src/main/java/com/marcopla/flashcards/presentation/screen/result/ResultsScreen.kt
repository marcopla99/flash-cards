package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.marcopla.flashcards.R

@Composable
fun ResultsScreen(
    modifier: Modifier = Modifier,
    onDoneClicked: () -> Unit,
) {
    Column {
        Text(stringResource(R.string.results))
        val buttonText = stringResource(id = R.string.resultsDone)
        Button(
            modifier = modifier.semantics { contentDescription = buttonText },
            onClick = {
                onDoneClicked()
            }
        ) {
            Text(buttonText)
        }
    }
}