package com.marcopla.flashcards.presentation.screen.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    onFlashCardEdited: () -> Unit,
    modifier: Modifier,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onFlashCardEdited) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.editButton)
                )
            }
        }
    ) {
        Column(modifier.padding(it)) {
            val backTextContentDescription = stringResource(R.string.backTextField)
            val frontTextContentDescription = stringResource(R.string.frontTextField)

            Text(text = stringResource(id = R.string.editScreenTitle))
            TextField(
                modifier = modifier.semantics {
                    contentDescription = frontTextContentDescription
                },
                value = viewModel.frontTextState.value.text,
                onValueChange = {}
            )
            TextField(
                modifier = modifier.semantics {
                    contentDescription = backTextContentDescription
                },
                value = viewModel.backTextState.value.text,
                onValueChange = {}
            )
        }
    }
}