package com.marcopla.flashcards.presentation.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcopla.flashcards.R
import com.marcopla.flashcards.presentation.screen.add.AddScreen
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
                    navController.navigate(Routes.EDIT_SCREEN)
                }
            )
        }
        composable(Routes.ADD_SCREEN) {
            AddScreen(modifier = modifier)
        }
        composable(Routes.EDIT_SCREEN) {
            EditScreen(modifier = modifier)
        }
    }
}

@Composable
fun EditScreen(modifier: Modifier) {
    Text(text = stringResource(id = R.string.editScreenTitle))
}

object Routes {
    const val HOME_SCREEN = "HOME_SCREEN"
    const val ADD_SCREEN = "ADD_SCREEN"
    const val EDIT_SCREEN = "EDIT_SCREEN"
}
