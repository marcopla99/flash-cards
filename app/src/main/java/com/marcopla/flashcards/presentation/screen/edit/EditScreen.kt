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
            onDismissRequest = { viewModel.hideDeleteConfirmationDialog() },
        )
    }

    var isEditing by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.editScreenTitle))
                },
                actions = {
                    if (isEditing) {
                        Icon(
                            modifier = modifier.clickable { TODO() },
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.resetButton)
                        )
                    } else {
                        Icon(
                            modifier = modifier.clickable {
                                viewModel.showDeleteConfirmationDialog()
                            },
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.deleteFlashCardButton),
                        )
                    }
                }
            )
        },
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
        LaunchedEffect(key1 = Unit) {
            viewModel.initState()
        }

        Column(
            modifier = modifier
                .padding(4.dp)
                .consumedWindowInsets(it)
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
                onValueChange = {
                    isEditing = true
                    viewModel.updateFrontText(it)
                },
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
                onValueChange = {
                    isEditing = true
                    viewModel.updateBackText(it)
                }
            )
        }
    }
}

@Composable
private fun HandleScreenState(
    screenState: EditScreenState,
    scaffoldState: ScaffoldState,
    onFlashCardEdited: () -> Unit,
    onFlashCardDeleted: () -> Unit,
) {
    when (screenState) {
        is EditScreenState.Error -> {
            val errorMessage = stringResource(id = R.string.duplicateCardError)
            LaunchedEffect(key1 = screenState.errorStringRes) {
                scaffoldState.snackbarHostState.showSnackbar(errorMessage)
            }
        }
        is EditScreenState.Edited -> {
            LaunchedEffect(key1 = screenState) { onFlashCardEdited() }
        }
        is EditScreenState.Initial -> {
            // Empty
        }
        EditScreenState.Deleted -> {
            onFlashCardDeleted()
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    onConfirmationClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismissRequest: () -> Unit,
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
        },
    )
}
