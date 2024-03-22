package com.sadri.starwars.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sadri.detail.navigation.detailScreen
import com.sadri.detail.navigation.navigateToDetailScreen
import com.sadri.search.navigation.searchRoute
import com.sadri.search.navigation.searchScreen
import com.sadri.starwars.ui.AppState

@Composable
fun NavHost(
  appState: AppState,
  modifier: Modifier = Modifier,
) {
  val navController = appState.navController
  NavHost(
    navController = navController,
    startDestination = searchRoute,
    modifier = modifier,
  ) {
    searchScreen { peopleEntity ->
      navController.navigateToDetailScreen(peopleEntity)
    }
    detailScreen()
  }
}