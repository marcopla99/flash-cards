package com.marcopla.flashcards.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            HomeScreen(modifier = modifier) {
                navController.navigate(Routes.ADD_SCREEN)
            }
        }
        composable(Routes.ADD_SCREEN) {
            AddScreen(modifier = modifier)
        }
    }
}

object Routes {
    const val HOME_SCREEN = "HOME_SCREEN"
    const val ADD_SCREEN = "ADD_SCREEN"
}
