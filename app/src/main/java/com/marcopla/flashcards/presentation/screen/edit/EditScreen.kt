package com.marcopla.flashcards.presentation.screen.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
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
fun EditScreen(
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel(),
    onFlashCardEdited: () -> Unit,
    onFlashCardDeleted: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    HandleScreenState(
        viewModel.screenState.value,
        scaffoldState,
        onFlashCardEdited,
        onFlashCardDeleted = {
            viewModel.hideDeleteConfirmationDialog()
            onFlashCardDeleted()
        }
    )

    if (viewModel.shouldShowDeleteConfirmation.value) {
        DeleteConfirmationDialog(
            modifier = modifier,
            onConfirmationClick = { viewModel.delete() },
            onCancelClick = { viewModel.hideDeleteConfirmationDialog() },
            onDismissRequest = { viewModel.hideDeleteConfirmationDialog() }
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.editScreenTitle))
                },
                actions = {
                    if (viewModel.screenState.value == EditScreenState.Editing) {
                        Icon(
                            modifier = modifier.clickable(onClick = viewModel::reset),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.resetButton)
                        )
                    } else {
                        Icon(
                            modifier = modifier.clickable {
                                viewModel.showDeleteConfirmationDialog()
                            },
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.deleteButton)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.attemptSubmit(
                    viewModel.frontTextState.value.text,
                    viewModel.backTextState.value.text
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.editButton)
                )
            }
        }
    ) {
        // TODO: Consider using Flows and call collectAsStateWithLifecycle here.
        LaunchedEffect(key1 = Unit) {
            viewModel.initState()
        }

        Column(
            modifier = modifier
                .padding(4.dp)
                .consumeWindowInsets(it)
        ) {
            val frontTextContentDescription = stringResource(R.string.frontTextField)
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = frontTextContentDescription
                    },
                value = viewModel.frontTextState.value.text,
                label = { Text(stringResource(R.string.frontTextFieldLabel)) },
                isError = viewModel.frontTextState.value.showError,
                onValueChange = viewModel::updateFrontText
            )

            Spacer(modifier = modifier.height(8.dp))

            val backTextContentDescription = stringResource(R.string.backTextField)
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = backTextContentDescription
                    },
                value = viewModel.backTextState.value.text,
                label = { Text(stringResource(R.string.backTextFieldLabel)) },
                isError = viewModel.backTextState.value.showError,
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
    onFlashCardDeleted: () -> Unit
) {
    when (screenState) {
        is EditScreenState.Error -> {
            val errorMessage = stringResource(id = R.string.duplicateCardError)
            LaunchedEffect(key1 = screenState.errorStringRes) {
                scaffoldState.snackbarHostState.showSnackbar(errorMessage)
            }
        }
        is EditScreenState.Edited -> {
            onFlashCardEdited()
        }
        is EditScreenState.Deleted -> {
            onFlashCardDeleted()
        }
        else -> {
            // Empty
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    onConfirmationClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.confirmDeletionDialogTitle)) },
        text = { Text(text = stringResource(id = R.string.confirmDeletionDialogBody)) },
        buttons = {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onConfirmationClick
                ) {
                    Text(stringResource(R.string.ok))
                }
                Button(
                    onClick = onCancelClick
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    )
}
