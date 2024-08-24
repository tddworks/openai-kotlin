package com.tddworks.common.network.api.ktor.internal

data class AuthConfig(
    val authToken: (() -> String)? = null
)