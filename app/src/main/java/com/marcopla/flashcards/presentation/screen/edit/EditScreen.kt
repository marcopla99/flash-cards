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
import com.marcopla.flashcards.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditScreen(
    onFlashCardEdited: () -> Unit,
    onFlashCardDeleted: () -> Unit,
    editScreenState: EditScreenState,
    onReset: () -> Unit,
    onShowDeleteConfirmationDialog: () -> Unit,
    onSubmit: () -> Unit,
    onInitState: () -> Unit,
    frontTextState: EditFrontTextState,
    onFrontTextValueChange: (String) -> Unit,
    backTextState: EditBackTextState,
    onBackTextValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberScaffoldState()
    HandleScreenState(
        editScreenState,
        scaffoldState,
        onFlashCardEdited,
        onFlashCardDeleted
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.editScreenTitle))
                },
                actions = {
                    if (editScreenState == EditScreenState.Editing) {
                        Icon(
                            modifier = Modifier.clickable(onClick = onReset),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.resetButton)
                        )
                    } else {
                        Icon(
                            modifier = Modifier.clickable(onClick = onShowDeleteConfirmationDialog),
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.deleteButton)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSubmit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.editButton)
                )
            }
        }
    ) {
        // TODO: Consider using Flows and call collectAsStateWithLifecycle here.
        LaunchedEffect(key1 = Unit, block = { onInitState() })

        Column(
            modifier = modifier
                .padding(4.dp)
                .consumeWindowInsets(it)
        ) {
            val frontTextContentDescription = stringResource(R.string.frontTextField)
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = frontTextContentDescription
                    },
                value = frontTextState.text,
                label = { Text(stringResource(R.string.frontTextFieldLabel)) },
                isError = frontTextState.showError,
                onValueChange = onFrontTextValueChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            val backTextContentDescription = stringResource(R.string.backTextField)
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = backTextContentDescription
                    },
                value = backTextState.text,
                label = { Text(stringResource(R.string.backTextFieldLabel)) },
                isError = backTextState.showError,
                onValueChange = onBackTextValueChange
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
fun DeleteConfirmationDialog(
    onConfirmationClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
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
