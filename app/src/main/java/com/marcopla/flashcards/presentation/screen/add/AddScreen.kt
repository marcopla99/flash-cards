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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R

@Composable
fun AddRoute(viewModel: AddViewModel = hiltViewModel()) {
    val infoTextState = viewModel.infoTextState.value
    val scaffoldState = rememberScaffoldState()
    HandleInfoTextEffect(
        infoTextState.messageStringRes,
        scaffoldState.snackbarHostState
    )

    AddScreen(
        frontTextState = viewModel.frontTextState.value,
        backTextState = viewModel.backTextState.value,
        addScreenState = viewModel.addScreenState.value,
        onSubmitClick = {
            viewModel.attemptSubmit(
                frontText = viewModel.frontTextState.value.text,
                backText = viewModel.backTextState.value.text
            )
        },
        onFrontTextValueChange = { frontInput -> viewModel.updateFrontText(frontInput) },
        onBackTextValueChange = { backInput -> viewModel.updateBackText(backInput) },
        scaffoldState = scaffoldState
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddScreen(
    frontTextState: FrontTextState,
    backTextState: BackTextState,
    addScreenState: AddScreenState,
    onSubmitClick: () -> Unit,
    onFrontTextValueChange: (String) -> Unit,
    onBackTextValueChange: (String) -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.addScreenTitle)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSubmitClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.addCardButton)
                )
            }
        }
    ) { paddingValues: PaddingValues ->
        Column(modifier = modifier.padding(4.dp).consumeWindowInsets(paddingValues)) {
            FontTextField(
                value = frontTextState.text,
                isError = frontTextState.showError,
                isFocused = addScreenState == AddScreenState.SUCCESSFUL_SAVE,
                onValueChange = onFrontTextValueChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            BackTextField(
                value = backTextState.text,
                isError = backTextState.showError,
                onValueChange = onBackTextValueChange
            )
        }
    }
}

@Composable
private fun FontTextField(
    value: String,
    isError: Boolean,
    isFocused: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val fieldContentDescription = stringResource(R.string.frontTextField)
    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = fieldContentDescription }
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
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val fieldContentDescription = stringResource(R.string.backTextField)
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = fieldContentDescription },
        value = value,
        isError = isError,
        label = { Text(stringResource(R.string.backTextFieldLabel)) },
        onValueChange = onValueChange
    )
}

@Composable
fun HandleInfoTextEffect(
    infoTextStringRes: Int?,
    snackbarHostState: SnackbarHostState
) {
    if (infoTextStringRes == null) return
    val infoText = stringResource(infoTextStringRes)
    LaunchedEffect(infoTextStringRes) {
        snackbarHostState.showSnackbar(infoText)
    }
}
