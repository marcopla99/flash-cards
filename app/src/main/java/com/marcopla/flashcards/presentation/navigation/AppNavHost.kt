package com.marcopla.flashcards.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marcopla.flashcards.presentation.screen.add.AddRoute
import com.marcopla.flashcards.presentation.screen.carousel.CarouselRoute
import com.marcopla.flashcards.presentation.screen.edit.EditRoute
import com.marcopla.flashcards.presentation.screen.home.HomeRoute
import com.marcopla.flashcards.presentation.screen.result.ResultsRoute

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN
    ) {
        composable(Routes.HOME_SCREEN) {
            HomeRoute(
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
            AddRoute()
        }
        composable(
            route = "${Routes.EDIT_SCREEN}/{$FLASH_CARD_ID_ARG_KEY}",
            arguments = listOf(
                navArgument(FLASH_CARD_ID_ARG_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            EditRoute(
                onPopBackStack = {
                    navController.popBackStack(Routes.HOME_SCREEN, false)
                }
            )
        }
        composable(Routes.CAROUSEL_SCREEN) {
            CarouselRoute(
                onLastFlashCardPlayed = {
                    navController.navigate(Routes.RESULT_SCREEN)
                }
            )
        }
        composable(Routes.RESULT_SCREEN) {
            ResultsRoute(
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
