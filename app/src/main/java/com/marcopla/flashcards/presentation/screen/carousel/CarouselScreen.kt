package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.FlashCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarouselScreen(
    modifier: Modifier = Modifier,
    flashCards: List<FlashCard>,
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.carouselScreenTitle))
            },
        )
    }) {
        Column(modifier = modifier.padding(4.dp).consumedWindowInsets(it)) {
            val solution = "Engels"
            Text(text = solution)

            var backTextValue by remember { mutableStateOf("") }
            TextField(
                value = backTextValue,
                onValueChange = { newValue -> backTextValue = newValue }
            )
        }
    }
}