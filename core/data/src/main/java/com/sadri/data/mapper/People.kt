package com.sadri.data.mapper

import com.sadri.model.PeopleEntity
import com.sadri.model.PeopleResultEntity
import com.sadri.network.model.PeopleItemResponse
import com.sadri.network.model.PeopleResultResponse

fun PeopleResultResponse.asEntity() = PeopleResultEntity(
  count = count,
  next = next,
  previous = previous,
  results = results.map { it.asEntity() }
)

fun PeopleItemResponse.asEntity() = PeopleEntity(
  name = name,
  birthYear = birth_year,
  height = height,
  films = films,
  species = species,
  homeWorld = homeworld
)