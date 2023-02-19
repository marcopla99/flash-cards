package com.marcopla.flashcards.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marcopla.flashcards.R
import com.marcopla.flashcards.presentation.screen.add.AddScreen
import com.marcopla.flashcards.presentation.screen.edit.EditViewModel
import com.marcopla.flashcards.presentation.screen.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
        modifier = modifier
    ) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(
                modifier = modifier,
                onNavigateToAddScreen = {
                    navController.navigate(Routes.ADD_SCREEN)
                },
                onItemClicked = {
                    navController.navigate("${Routes.EDIT_SCREEN}/$it")
                }
            )
        }
        composable(Routes.ADD_SCREEN) {
            AddScreen(modifier = modifier)
        }
        composable(
            "${Routes.EDIT_SCREEN}/{$FLASH_CARD_ID_ARG_KEY}",
            arguments = listOf(
                navArgument(FLASH_CARD_ID_ARG_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            EditScreen(modifier = modifier, onFlashCardEdited = {
                navController.popBackStack()
            })
        }
    }
}

const val FLASH_CARD_ID_ARG_KEY = "flashCardId"

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    onFlashCardEdited: () -> Unit,
    modifier: Modifier,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onFlashCardEdited) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.editButton)
                )
            }
        }
    ) {
        Column(modifier.padding(it)) {
            val backTextContentDescription = stringResource(R.string.backTextField)
            val frontTextContentDescription = stringResource(R.string.frontTextField)

            Text(text = stringResource(id = R.string.editScreenTitle))
            TextField(
                modifier = modifier.semantics {
                    contentDescription = frontTextContentDescription
                },
                value = viewModel.frontTextState.value.text,
                onValueChange = {}
            )
            TextField(
                modifier = modifier.semantics {
                    contentDescription = backTextContentDescription
                },
                value = viewModel.backTextState.value.text,
                onValueChange = {}
            )
        }
    }
}

object Routes {
    const val HOME_SCREEN = "HOME_SCREEN"
    const val ADD_SCREEN = "ADD_SCREEN"
    const val EDIT_SCREEN = "EDIT_SCREEN"
}
