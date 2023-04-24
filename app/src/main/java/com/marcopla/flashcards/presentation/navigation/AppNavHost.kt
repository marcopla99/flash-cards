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
import com.marcopla.flashcards.presentation.screen.edit.DeleteConfirmationDialog
import com.marcopla.flashcards.presentation.screen.edit.EditScreen
import com.marcopla.flashcards.presentation.screen.edit.EditViewModel
import com.marcopla.flashcards.presentation.screen.home.HomeScreen
import com.marcopla.flashcards.presentation.screen.home.HomeScreenState
import com.marcopla.flashcards.presentation.screen.home.HomeViewModel
import com.marcopla.flashcards.presentation.screen.result.ResultsScreen

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

@Composable
fun AddRoute(viewModel: AddViewModel = hiltViewModel()) {
    AddScreen(
        infoTextState = viewModel.infoTextState.value,
        frontTextState = viewModel.frontTextState.value,
        backTextState = viewModel.backTextState.value,
        addScreenState = viewModel.addScreenState.value,
        onSubmitClick = {
            viewModel.attemptSubmit(
                frontText = viewModel.frontTextState.value.text,
                backText = viewModel.backTextState.value.text
            )
        },
        onFrontTextValueChange = { frontInput -> viewModel.updateFrontText(frontInput) },
        onBackTextValueChange = { backInput -> viewModel.updateBackText(backInput) }
    )
}

@Composable
fun EditRoute(
    viewModel: EditViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    if (viewModel.shouldShowDeleteConfirmation.value) {
        DeleteConfirmationDialog(
            onConfirmationClick = viewModel::delete,
            onCancelClick = viewModel::hideDeleteConfirmationDialog,
            onDismissRequest = viewModel::hideDeleteConfirmationDialog
        )
    }

    EditScreen(
        onFlashCardEdited = onPopBackStack,
        onFlashCardDeleted = {
            viewModel.hideDeleteConfirmationDialog()
            onPopBackStack()
        },
        editScreenState = viewModel.screenState.value,
        onReset = viewModel::reset,
        onShowDeleteConfirmationDialog = viewModel::showDeleteConfirmationDialog,
        onSubmit = {
            viewModel.attemptSubmit(
                viewModel.frontTextState.value.text,
                viewModel.backTextState.value.text
            )
        },
        onInitState = viewModel::initState,
        frontTextState = viewModel.frontTextState.value,
        onFrontTextValueChange = viewModel::updateFrontText,
        backTextState = viewModel.backTextState.value,
        onBackTextValueChange = viewModel::updateBackText
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
