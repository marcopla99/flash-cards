package com.marcopla.flashcards.presentation.screen.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
                FontTextField(
                    value = viewModel.frontTextState.value.text,
                    isError = viewModel.frontTextState.value.showError,
                    isFocused = viewModel.screenState.value == ScreenState.SUCCESSFUL_SAVE,
                    onValueChange = { frontInput -> viewModel.updateFrontText(frontInput) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                BackTextField(
                    viewModel.backTextState.value.text,
                    isError = viewModel.backTextState.value.showError,
                    onValueChange = { backInput -> viewModel.updateBackText(backInput) }
                )
            }
        }
    }
}

@Composable
private fun FontTextField(
    value: String,
    isError: Boolean,
    isFocused: Boolean,
    onValueChange: (String) -> Unit
) {
    val frontTextFieldCd = stringResource(R.string.frontTextFieldCd)
    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = frontTextFieldCd }
            .focusRequester(focusRequester),
        value = value,
        isError = isError,
        label = { Text(stringResource(R.string.frontTextFieldLabel)) },
        onValueChange = onValueChange
    )
    if (isFocused) {
        LaunchedEffect(key1 = Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun BackTextField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    val backTextFieldCd = stringResource(R.string.backTextFieldCd)
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = backTextFieldCd },
        value = value,
        isError = isError,
        label = { Text(stringResource(R.string.backTextFieldLabel)) },
        onValueChange = onValueChange
    )
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