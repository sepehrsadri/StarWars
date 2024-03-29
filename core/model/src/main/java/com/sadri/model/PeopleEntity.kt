package com.sadri.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeopleEntity(
  val name: String,
  val birthYear: String,
  val height: String,
  val films: List<String>,
  val species: List<String>,
  val homeWorld: String
) : Parcelable
