package com.tddworks.common.network.api.ktor.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AnySerializerTest {

    @Test
    fun `should deserialize others like null object`() {
        // Given
        val serialized = "{\"any\":{\"key\":null}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(AnySerialObject(mapOf("key" to "null")), deserialized)
    }

    @Test
    fun `should deserialize map object`() {
        // Given
        val serialized = "{\"any\":{\"key\":{\"nestedKey\":\"nestedValue\"}}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(
            AnySerialObject(mapOf("key" to mapOf("nestedKey" to "nestedValue"))),
            deserialized
        )
    }

    @Test
    fun `should deserialize double object`() {
        // Given
        val serialized = "{\"any\":{\"key\":1.0}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(AnySerialObject(mapOf("key" to 1.0)), deserialized)
    }

    @Test
    fun `should deserialize int object`() {
        // Given
        val serialized = "{\"any\":{\"key\":1}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(AnySerialObject(mapOf("key" to 1)), deserialized)
    }

    @Test
    fun `should deserialize boolean object`() {
        // Given
        val serialized = "{\"any\":{\"key\":true}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(AnySerialObject(mapOf("key" to true)), deserialized)
    }

    @Test
    fun `should deserialize list object`() {
        // Given
        val serialized = "{\"any\":{\"key\":[\"value1\",\"value2\"]}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(
            AnySerialObject(mapOf("key" to listOf("value1", "value2"))),
            deserialized
        )
    }

    @Test
    fun `should deserialize string object`() {
        // Given
        val serialized = "{\"any\":{\"key\":\"value\"}}"

        // When
        val deserialized = Json.decodeFromString(AnySerialObject.serializer(), serialized)

        // Then
        assertEquals(AnySerialObject(mapOf("key" to "value")), deserialized)
    }

    @Test
    fun `should serialize list object`() {
        // Given
        val testObject = AnySerialObject(mapOf("key" to listOf("value1", "value2")))

        // When
        val serialized = Json.encodeToString(AnySerialObject.serializer(), testObject)

        // Then
        assertEquals("{\"any\":{\"key\":[\"value1\",\"value2\"]}}", serialized)
    }

    @Test
    fun `should serialize map object`() {
        // Given
        val testObject =
            AnySerialObject(mapOf("key" to mapOf("nestedKey" to "nestedValue")))

        // When
        val serialized = Json.encodeToString(AnySerialObject.serializer(), testObject)

        // Then
        assertEquals("{\"any\":{\"key\":{\"nestedKey\":\"nestedValue\"}}}", serialized)
    }


    @Test
    fun `should serialize boolean object`() {
        // Given
        val testObject = AnySerialObject(mapOf("key" to true))

        // When
        val serialized = Json.encodeToString(AnySerialObject.serializer(), testObject)

        // Then
        assertEquals("{\"any\":{\"key\":true}}", serialized)
    }

    @Test
    fun `should serialize string object`() {
        // Given
        val testObject = AnySerialObject(mapOf("key" to "value"))

        // When
        val serialized = Json.encodeToString(AnySerialObject.serializer(), testObject)

        // Then
        assertEquals("{\"any\":{\"key\":\"value\"}}", serialized)
    }

    @Test
    fun `should serialize int object`() {
        // Given
        val testObject = AnySerialObject(mapOf("key" to 1))

        // When
        val serialized = Json.encodeToString(AnySerialObject.serializer(), testObject)

        // Then
        assertEquals("{\"any\":{\"key\":1}}", serialized)
    }

}

@Serializable
data class AnySerialObject(val any: Map<String, AnySerial>)


