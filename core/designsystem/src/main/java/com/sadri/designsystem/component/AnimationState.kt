package com.sadri.designsystem.component

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimationState {

  private val _runAnimationState = MutableStateFlow(false)
  val runAnimationState: StateFlow<Boolean> = _runAnimationState.asStateFlow()

  fun startAnimation() {
    _runAnimationState.value = true
  }

  fun onAnimationEnd() {
    _runAnimationState.value = false
  }
}