package com.sadri.designsystem.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes

private const val X_END_POSITION = 8f
private const val X_START_POSITION = -8f
private const val X_CENTER_POSITION = 0f

private const val X_ANIMATION_COUNT = 3
private const val X_ANIMATION_FRAME_COUNT = 8

private const val ANIMATION_DURATION = 800

val shakeKeyframes: AnimationSpec<Float> = keyframes {
  durationMillis = ANIMATION_DURATION
  val easing = FastOutLinearInEasing

  for (i in 1..X_ANIMATION_FRAME_COUNT) {
    val offsetX = when (i % X_ANIMATION_COUNT) {
      0 -> X_END_POSITION
      1 -> X_START_POSITION
      else -> X_CENTER_POSITION
    }
    offsetX at durationMillis / 10 * i with easing
  }
}