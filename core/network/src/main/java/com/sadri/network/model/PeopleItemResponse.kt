package com.sadri.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PeopleItemResponse(
  val name: String,
  val birth_year: String,
  val height: String,
  val films: List<String>,
  val species: List<String>,
  val homeworld: String
)
