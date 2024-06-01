package com.tddworks.openai.api.legacy.completions.api

/**
 * https://platform.openai.com/docs/api-reference/completions
 * Given a prompt, the model will return one or more predicted completions along with the probabilities of alternative tokens at each position. Most developer should use our Chat Completions API to leverage our best and newest models.
 */
interface Completions {
    suspend fun completions(request: CompletionRequest): Completion
}