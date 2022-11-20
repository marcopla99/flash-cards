package com.marcopla.flashcards.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcopla.flashcards.add.AddScreen
import com.marcopla.flashcards.home.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen {
                navController.navigate(Routes.ADD_SCREEN)
            }
        }
        composable(Routes.ADD_SCREEN) {
            AddScreen()
        }
    }
}

object Routes {
    const val HOME_SCREEN = "HOME_SCREEN"
    const val ADD_SCREEN = "ADD_SCREEN"
}
