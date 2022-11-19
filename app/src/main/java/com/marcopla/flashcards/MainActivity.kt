package com.marcopla.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.marcopla.flashcards.data.FlashCard
import com.marcopla.flashcards.navigation.AppNavHost
import com.marcopla.flashcards.ui.theme.FlashCardsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashCardsTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun HomePage(onNavigateToAddPage: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddPage
            ) {
                Icon(
                    contentDescription = stringResource(R.string.addFlashCardButtonCd),
                    imageVector = Icons.Default.Add
                )
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            ContentSection(flashCards = listOf())
        }
    }
}

@Composable
fun ContentSection(flashCards: List<FlashCard>) {
    if (flashCards.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.noFlashCardsCreated),
            textAlign = TextAlign.Center
        )
    } else {
        TODO()
    }
}

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