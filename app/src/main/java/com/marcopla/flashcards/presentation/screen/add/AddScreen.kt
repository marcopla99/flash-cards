package com.marcopla.flashcards.presentation.screen.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcopla.flashcards.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun AddScreen(
    viewModel: NewFlashCardViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val infoMessage = stringResource(R.string.cardAdded)
    Scaffold(
        modifier = Modifier.padding(8.dp),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(infoMessage)
                }
            }) {
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
                var frontText by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = frontTextFieldCd },
                    value = frontText,
                    label = { Text(stringResource(R.string.frontTextFieldLabel)) },
                    onValueChange = { frontInput -> frontText = frontInput }
                )
                Spacer(modifier = Modifier.height(8.dp))
                val backTextFieldCd = stringResource(R.string.backTextFieldCd)
                var backText by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = backTextFieldCd },
                    value = backText,
                    label = { Text(stringResource(R.string.backTextFieldLabel)) },
                    onValueChange = { backInput -> backText = backInput }
                )
            }
        }
    }
}