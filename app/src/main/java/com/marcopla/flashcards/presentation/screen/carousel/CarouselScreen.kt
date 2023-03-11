package com.marcopla.flashcards.presentation.screen.carousel

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marcopla.flashcards.R

@Composable
fun CarouselScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.carouselScreenTitle))
            },
        )
    }) {
        val ignore = it
    }
}