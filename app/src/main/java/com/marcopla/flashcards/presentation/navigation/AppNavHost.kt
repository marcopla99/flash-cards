package com.marcopla.flashcards.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marcopla.flashcards.presentation.screen.add.AddScreen
import com.marcopla.flashcards.presentation.screen.add.AddViewModel
import com.marcopla.flashcards.presentation.screen.carousel.CarouselScreen
import com.marcopla.flashcards.presentation.screen.edit.EditScreen
import com.marcopla.flashcards.presentation.screen.home.HomeScreen
import com.marcopla.flashcards.presentation.screen.home.HomeScreenState
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import com.marcopla.flashcards.presentation.screen.result.ResultsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    addViewModel: AddViewModel = hiltViewModel()
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
            AddScreen(
                infoTextState = addViewModel.infoTextState.value,
                frontTextState = addViewModel.frontTextState.value,
                backTextState = addViewModel.backTextState.value,
                addScreenState = addViewModel.addScreenState.value,
                onSubmitClick = {
                    addViewModel.attemptSubmit(
                        frontText = addViewModel.frontTextState.value.text,
                        backText = addViewModel.backTextState.value.text
                    )
                },
                onFrontTextValueChange = { frontInput -> addViewModel.updateFrontText(frontInput) },
                onBackTextValueChange = { backInput -> addViewModel.updateBackText(backInput) }
            )
        }
        composable(
            route = "${Routes.EDIT_SCREEN}/{$FLASH_CARD_ID_ARG_KEY}",
            arguments = listOf(
                navArgument(FLASH_CARD_ID_ARG_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            EditScreen(
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
                onLastFlashCardPlayed = {
                    navController.navigate(Routes.RESULT_SCREEN)
                }
            )
        }
        composable(Routes.RESULT_SCREEN) {
            ResultsScreen(
                onDoneClicked = {
                    navController.popBackStack(Routes.HOME_SCREEN, false)
                }
            )
        }
    }
}

@Composable
private fun HomeRoute(
    onNavigateToAddScreen: () -> Unit,
    onItemClicked: (Int) -> Unit,
    onNavigateToCarouselScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val screenState: HomeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    HomeScreen(
        screenState = screenState,
        onNavigateToAddScreen = onNavigateToAddScreen,
        onItemClicked = onItemClicked,
        onNavigateToCarouselScreen = onNavigateToCarouselScreen
    )
}

object Routes {
    const val CAROUSEL_SCREEN = "CAROUSEL_SCREEN"
    const val HOME_SCREEN = "HOME_SCREEN"
    const val ADD_SCREEN = "ADD_SCREEN"
    const val EDIT_SCREEN = "EDIT_SCREEN"
    const val RESULT_SCREEN = "RESULT_SCREEN"
}

const val FLASH_CARD_ID_ARG_KEY = "flashCardId"
