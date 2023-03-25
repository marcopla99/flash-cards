package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.marcopla.flashcards.data.model.QuizResult

@Composable
fun ResultItem(
    modifier: Modifier = Modifier,
    quizResult: QuizResult,
) {
    Card {
        Row {
            Column {
                Text(text = quizResult.flashCard.frontText)
                Text(text = quizResult.flashCard.backText)
            }
            Text(text = quizResult.guess)
        }
    }
}
