package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.Serializable

@Serializable
data class ContentMessage(val text: String, val type: String)