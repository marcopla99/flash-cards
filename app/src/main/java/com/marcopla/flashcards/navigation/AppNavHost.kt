package com.marcopla.flashcards.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcopla.flashcards.add.AddPage
import com.marcopla.flashcards.home.HomePage

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME_PAGE) {
        composable(Routes.HOME_PAGE) {
            HomePage {
                navController.navigate(Routes.ADD_PAGE)
            }
        }
        composable(Routes.ADD_PAGE) {
            AddPage()
        }
    }
}

object Routes {
    const val HOME_PAGE = "HOME_PAGE"
    const val ADD_PAGE = "ADD_PAGE"
}
