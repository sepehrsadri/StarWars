package com.sadri.model

data class PeopleResultEntity(
  val count: Int,
  val next: String?,
  val previous: String?,
  val results: List<PeopleEntity>
)