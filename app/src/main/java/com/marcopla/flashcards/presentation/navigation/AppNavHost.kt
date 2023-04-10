package com.marcopla.flashcards.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marcopla.flashcards.presentation.screen.add.AddScreen
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreen
import com.marcopla.flashcards.presentation.screen.edit.EditScreen
import com.marcopla.flashcards.presentation.screen.home.HomeScreen
import com.marcopla.flashcards.presentation.screen.result.ResultsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
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
                },
                onNavigateToCarouselScreen = {
                    navController.navigate(Routes.CAROUSEL_SCREEN)
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
            EditScreen(
                modifier = modifier,
                onFlashCardEdited = {
                    navController.popBackStack(Routes.HOME_SCREEN, false)
                },
                onFlashCardDeleted = {
                    navController.popBackStack(Routes.HOME_SCREEN, false)
                }
            )
        }
        composable(Routes.CAROUSEL_SCREEN) {
            CarouselScreen(
                modifier = modifier,
                onLastFlashCardPlayed = {
                    navController.navigate(Routes.RESULT_SCREEN)
                }
            )
        }
        composable(Routes.RESULT_SCREEN) {
            ResultsScreen(
                modifier = modifier,
                onDoneClicked = {
                    navController.popBackStack(Routes.HOME_SCREEN, false)
                }
            )
        }
    }
}

object Routes {
    const val CAROUSEL_SCREEN = "CAROUSEL_SCREEN"
    const val HOME_SCREEN = "HOME_SCREEN"
    const val ADD_SCREEN = "ADD_SCREEN"
    const val EDIT_SCREEN = "EDIT_SCREEN"
    const val RESULT_SCREEN = "RESULT_SCREEN"
}

const val FLASH_CARD_ID_ARG_KEY = "flashCardId"
