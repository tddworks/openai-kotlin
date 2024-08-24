package com.tddworks.common.network.api.ktor.internal

data class HostPortConnectionConfig(
    val protocol: () -> String? = { null },
    val port: () -> Int? = { null },
    val host: () -> String,
) : ConnectionConfig