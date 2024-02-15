package com.tddworks.openai.api.internal.network.ktor.exception

/** OpenAI client exception */
sealed class OpenAIException(
    message: String? = null,
    throwable: Throwable? = null,
) : RuntimeException(message, throwable)

/** Runtime Http Client exception */
class OpenAIHttpException(
    throwable: Throwable? = null,
) : OpenAIException(throwable?.message, throwable)

/** An exception thrown in case of a server error */
class OpenAIServerException(
    throwable: Throwable? = null,
) : OpenAIException(message = throwable?.message, throwable = throwable)
