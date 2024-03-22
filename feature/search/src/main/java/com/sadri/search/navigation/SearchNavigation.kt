package com.sadri.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sadri.model.PeopleEntity
import com.sadri.search.SearchRoute

const val searchRoute = "searchRoute"

fun NavGraphBuilder.searchScreen(onPeopleClicked: (PeopleEntity) -> Unit) {
  composable(searchRoute) {
    SearchRoute(onPeopleClicked = onPeopleClicked)
  }
}