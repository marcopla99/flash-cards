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
    val message = stringResource(id = R.string.duplicateCardError)
    val editScreenState = viewModel.screenState.value as? EditScreenState.Error
    val errorStringRes = editScreenState?.errorStringRes
    if (errorStringRes != null) {
        LaunchedEffect(key1 = errorStringRes) {
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.attemptSubmit(
                    viewModel.frontTextState.value.text,
                    viewModel.backTextState.value.text,
                )
                onFlashCardEdited() // FIXME
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