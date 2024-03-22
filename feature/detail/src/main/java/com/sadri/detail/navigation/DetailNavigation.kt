package com.sadri.detail.navigation

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.sadri.detail.DetailRoute
import com.sadri.model.PeopleEntity

const val detailRoute = "detailRoute"
const val PEOPLE_ENTITY_ARG = "peopleEntity"

fun NavGraphBuilder.detailScreen() {
  composable(
    route = "detailRoute/{$PEOPLE_ENTITY_ARG}",
    arguments = listOf(
      navArgument(PEOPLE_ENTITY_ARG) { type = PeopleEntityParamType() }
    )
  ) {
    DetailRoute()
  }
}

fun NavController.navigateToDetailScreen(
  peopleEntity: PeopleEntity
) {
  val json = Uri.encode(Gson().toJson(peopleEntity))
  navigate("$detailRoute/$json"){
    restoreState = true
  }
}

class PeopleEntityParamType : NavType<PeopleEntity>(isNullableAllowed = false) {
  override fun get(bundle: Bundle, key: String): PeopleEntity? {
    return BundleCompat.getParcelable(bundle, key, PeopleEntity::class.java)
  }

  override fun parseValue(value: String): PeopleEntity {
    return Gson().fromJson(value, PeopleEntity::class.java)
  }

  override fun put(bundle: Bundle, key: String, value: PeopleEntity) {
    bundle.putParcelable(key, value)
  }
}


