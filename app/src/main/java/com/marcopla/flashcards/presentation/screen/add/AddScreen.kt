package com.marcopla.flashcards.presentation.screen.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R

@Preview
@Composable
fun AddScreen(
    viewModel: NewFlashCardViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    HandleInfoTextEffect(
        viewModel.infoTextState.value.messageStringRes,
        scaffoldState.snackbarHostState
    )

    Scaffold(
        modifier = Modifier.padding(8.dp),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.attemptSubmit(
                        frontText = viewModel.frontTextState.value.text,
                        backText = viewModel.backTextState.value.text,
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.addCardButtonCd)
                )
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Column {
                val frontTextFieldCd = stringResource(R.string.frontTextFieldCd)
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = frontTextFieldCd },
                    value = viewModel.frontTextState.value.text,
                    isError = viewModel.frontTextState.value.showError,
                    label = { Text(stringResource(R.string.frontTextFieldLabel)) },
                    onValueChange = { frontInput -> viewModel.updateFrontText(frontInput) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                val backTextFieldCd = stringResource(R.string.backTextFieldCd)
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = backTextFieldCd },
                    value = viewModel.backTextState.value.text,
                    isError = viewModel.backTextState.value.showError,
                    label = { Text(stringResource(R.string.backTextFieldLabel)) },
                    onValueChange = { backInput -> viewModel.updateBackText(backInput) }
                )
            }
        }
    }
}

@Composable
private fun HandleInfoTextEffect(
    infoTextStringRes: Int?,
    snackbarHostState: SnackbarHostState
) {
    if (infoTextStringRes == null) return
    val infoText = stringResource(infoTextStringRes)
    LaunchedEffect(infoTextStringRes) {
        snackbarHostState.showSnackbar(infoText)
    }
}