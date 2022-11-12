package com.marcopla.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import com.marcopla.flashcards.data.FlashCard
import com.marcopla.flashcards.navigation.AppNavHost
import com.marcopla.flashcards.ui.theme.FlashCardsTheme

const val ADD_PAGE_ROUTE = "Add Flash Card"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}

@Composable
fun HomePage(onNavigateToAddPage: () -> Unit) {
    FlashCardsTheme {
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
            }
        }
        ContentSection(flashCards = listOf())
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

@Composable
fun AddPage() {
}