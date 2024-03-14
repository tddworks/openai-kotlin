package com.tddworks.common.network.api.ktor.internal.exception;

/**
 * An exception thrown in case of an I/O error
 */
sealed class OpenAIIOException(
    throwable: Throwable? = null,
) : OpenAIException(message = throwable?.message, throwable = throwable)

/**
 * An exception thrown in case a request times out.
 */
class OpenAITimeoutException(
    throwable: Throwable,
) : OpenAIIOException(throwable = throwable)

/**
 * An exception thrown in case of an I/O error
 */
class GenericIOException(
    throwable: Throwable? = null,
) : OpenAIIOException(throwable = throwable)
