package com.sadri.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PeopleResultResponse(
  val count: Int,
  val next: String?,
  val previous: String?,
  val results: List<PeopleItemResponse>
)
