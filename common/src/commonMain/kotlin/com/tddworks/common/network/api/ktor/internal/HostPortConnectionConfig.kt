package com.tddworks.common.network.api.ktor.internal

data class HostPortConnectionConfig(
    val protocol: () -> String? = { null },
    val host: () -> String = { "" },
    val port: () -> Int? = { null },
) : ConnectionConfig