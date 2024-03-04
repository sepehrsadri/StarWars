package com.sadri.testing.util

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import java.util.Timer
import kotlin.concurrent.schedule

private const val WAIT_UNTIL_TIMEOUT = 10_000L

fun ComposeContentTestRule.waitUntilNodeCount(
  matcher: SemanticsMatcher,
  count: Int,
  timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
) {
  waitUntil(timeoutMillis) {
    onAllNodes(matcher).fetchSemanticsNodes().size == count
  }
}

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilExists(
  matcher: SemanticsMatcher,
  timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
) = waitUntilNodeCount(matcher, 1, timeoutMillis)

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilDoesNotExist(
  matcher: SemanticsMatcher,
  timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
) = waitUntilNodeCount(matcher, 0, timeoutMillis)

fun ComposeContentTestRule.waitUntilTimeout(
  timeoutMillis: Long
) {
  AsyncTimer.start(timeoutMillis)
  this.waitUntil(
    condition = { AsyncTimer.expired },
    timeoutMillis = timeoutMillis + 1000
  )
}

object AsyncTimer {
  var expired = false
  fun start(delay: Long = 1000) {
    expired = false
    Timer().schedule(delay) {
      expired = true
    }
  }
}