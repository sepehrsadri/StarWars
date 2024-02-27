package com.sadri.model

open class LocalException(
  message: String? = null,
  cause: Throwable? = null
) : Throwable(message, cause)