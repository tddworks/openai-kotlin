package com.tddworks.common.network.api.ktor.internal

data class UrlBasedConnectionConfig(val baseUrl: () -> String = { "" }) : ConnectionConfig
