package com.marcopla.flashcards.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.marcopla.flashcards.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun AddPage() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val infoMessage = stringResource(R.string.cardAdded)
    Scaffold(
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
                    modifier = Modifier.semantics { contentDescription = frontTextFieldCd },
                    value = frontText,
                    label = { Text(stringResource(R.string.fronTextFieldLabel)) },
                    onValueChange = { frontInput -> frontText = frontInput }
                )
                val backTextFieldCd = stringResource(R.string.backTextFieldCd)
                var backText by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier.semantics { contentDescription = backTextFieldCd },
                    value = backText,
                    label = { Text(stringResource(R.string.backTextFieldLabel)) },
                    onValueChange = { backInput -> backText = backInput }
                )
            }
        }
    }
}