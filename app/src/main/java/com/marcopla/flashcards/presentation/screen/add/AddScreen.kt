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
import com.marcopla.flashcards.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddScreen(
    infoTextState: InfoTextState,
    frontTextState: FrontTextState,
    backTextState: BackTextState,
    addScreenState: AddScreenState,
    onSubmitClick: () -> Unit,
    onFrontTextValueChange: (String) -> Unit,
    onBackTextValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberScaffoldState()
    HandleInfoTextEffect(
        infoTextState.messageStringRes,
        scaffoldState.snackbarHostState
    )

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