package com.tddworks.anthropic.api.messages.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class RoleTest {

    @Test
    fun `should return correct role name`() {
        assertEquals("user", Role.User.name)
        assertEquals("assistant", Role.Assistant.name)
    }
}