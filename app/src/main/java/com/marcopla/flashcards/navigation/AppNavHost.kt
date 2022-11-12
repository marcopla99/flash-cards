package com.marcopla.flashcards.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcopla.flashcards.ADD_PAGE_ROUTE
import com.marcopla.flashcards.AddPage
import com.marcopla.flashcards.HomePage

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "HomePage") {
        composable("HomePage") {
            HomePage {
                navController.navigate(ADD_PAGE_ROUTE)
            }
        }
        composable(ADD_PAGE_ROUTE) {
            AddPage()
        }
    }
}
