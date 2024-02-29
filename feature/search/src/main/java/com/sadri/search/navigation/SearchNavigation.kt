package com.sadri.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sadri.search.SearchRoute

const val searchRoute = "search"

fun NavGraphBuilder.searchScreen() {
  composable(searchRoute) {
    SearchRoute()
  }
}