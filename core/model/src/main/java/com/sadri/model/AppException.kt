package com.sadri.model

sealed class AppException(
  message: String? = null,
  cause: Throwable? = null
) : LocalException(message, cause) {

  data object Unexpected : AppException(
    message = "Something went wrong! = Unexpected code"
  )

  data class IO(
    override val cause: Throwable
  ) : AppException(
    message = "Something went wrong! = IO code",
    cause = cause
  )

  class Http(
    override val cause: Throwable
  ) : AppException(
    message = "Something went wrong! = Http code",
    cause = cause
  )

  class Disconnected(
    override val cause: Throwable
  ) : AppException(
    message = "Something went wrong! = No network connection code",
    cause = cause
  )

  data object Empty : AppException(
    message = "Something went wrong! = no items found"
  )
}