package com.marcopla.flashcards.presentation.screen.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel(),
    onFlashCardEdited: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    HandleScreenState(viewModel.screenState.value, scaffoldState, onFlashCardEdited)

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.attemptSubmit(
                    viewModel.frontTextState.value.text,
                    viewModel.backTextState.value.text,
                )
            }) {
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
                onValueChange = viewModel::updateFrontText,
            )
            TextField(
                modifier = modifier.semantics {
                    contentDescription = backTextContentDescription
                },
                value = viewModel.backTextState.value.text,
                onValueChange = viewModel::updateBackText
            )
        }
    }
}

@Composable
private fun HandleScreenState(
    screenState: EditScreenState,
    scaffoldState: ScaffoldState,
    onFlashCardEdited: () -> Unit,
) {
    when (screenState) {
        EditScreenState.Editing -> {
            // TODO
        }
        is EditScreenState.Error -> {
            val errorMessage = stringResource(id = R.string.duplicateCardError)
            LaunchedEffect(key1 = screenState.errorStringRes) {
                scaffoldState.snackbarHostState.showSnackbar(errorMessage)
            }
        }
        EditScreenState.Success -> {
            LaunchedEffect(key1 = screenState) { onFlashCardEdited() }
        }
    }
}