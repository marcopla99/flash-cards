package com.marcopla.flashcards.presentation.screen.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.marcopla.flashcards.R
import com.marcopla.flashcards.data.model.QuizResult

@Composable
fun ResultItem(
    modifier: Modifier = Modifier,
    quizResult: QuizResult,
) {
    Card {
        Row(
            modifier = modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = quizResult.flashCard.frontText)
                Text(text = quizResult.flashCard.backText)
            }

            val stringResource = stringResource(id = R.string.guessResponse)
            if (!quizResult.isCorrect) {
                Text(
                    text = quizResult.guess,
                    modifier = modifier.semantics {
                        contentDescription = stringResource
                    }
                )
            }
        }
    }
}
