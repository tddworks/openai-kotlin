package com.tddworks.ollama.api.generate

import com.tddworks.ollama.api.json.JsonLenient
import com.tddworks.ollama.api.prettyJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OllamaGenerateRequestTest {

    @Test
    fun `should convert to json from object`() {
        // given
        val request =
            OllamaGenerateRequest(
                model = "llama3",
                prompt = "Why is the sky blue?",
                stream = false,
                raw = true,
                options =
                    mapOf(
                        "num_keep" to 5,
                        "seed" to 42,
                        "num_predict" to 100,
                        "top_k" to 20,
                        "top_p" to 0.9,
                        "tfs_z" to 0.5,
                        "typical_p" to 0.7,
                        "repeat_last_n" to 33,
                        "temperature" to 0.8,
                        "repeat_penalty" to 1.2,
                        "presence_penalty" to 1.5,
                        "frequency_penalty" to 1.0,
                        "mirostat" to 1,
                        "mirostat_tau" to 0.8,
                        "mirostat_eta" to 0.6,
                        "penalize_newline" to true,
                        "stop" to listOf("\n", "user:"),
                        "numa" to false,
                        "num_ctx" to 1024,
                        "num_batch" to 2,
                        "num_gpu" to 1,
                        "main_gpu" to 0,
                        "low_vram" to false,
                        "f16_kv" to true,
                        "vocab_only" to false,
                        "use_mmap" to true,
                        "use_mlock" to false,
                        "num_thread" to 8,
                    ),
            )

        // when
        val json = prettyJson.encodeToString(OllamaGenerateRequest.serializer(), request)

        // then
        assertEquals(
            """
                {
                  "model": "llama3",
                  "prompt": "Why is the sky blue?",
                  "stream": false,
                  "raw": true,
                  "options": {
                    "num_keep": 5,
                    "seed": 42,
                    "num_predict": 100,
                    "top_k": 20,
                    "top_p": 0.9,
                    "tfs_z": 0.5,
                    "typical_p": 0.7,
                    "repeat_last_n": 33,
                    "temperature": 0.8,
                    "repeat_penalty": 1.2,
                    "presence_penalty": 1.5,
                    "frequency_penalty": 1.0,
                    "mirostat": 1,
                    "mirostat_tau": 0.8,
                    "mirostat_eta": 0.6,
                    "penalize_newline": true,
                    "stop": [
                      "\n",
                      "user:"
                    ],
                    "numa": false,
                    "num_ctx": 1024,
                    "num_batch": 2,
                    "num_gpu": 1,
                    "main_gpu": 0,
                    "low_vram": false,
                    "f16_kv": true,
                    "vocab_only": false,
                    "use_mmap": true,
                    "use_mlock": false,
                    "num_thread": 8
                  }
                }
            """
                .trimIndent(),
            json,
        )
    }

    @Test
    fun `should convert json to object`() {
        // given
        val json =
            """
        {
            "model": "llama3",
            "prompt": "Why is the sky blue?",
            "stream": false,
            "raw": true,
            "options": {
                "num_keep": 5,
                "seed": 42,
                "num_predict": 100,
                "top_k": 20,
                "top_p": 0.9,
                "tfs_z": 0.5,
                "typical_p": 0.7,
                "repeat_last_n": 33,
                "temperature": 0.8,
                "repeat_penalty": 1.2,
                "presence_penalty": 1.5,
                "frequency_penalty": 1.0,
                "mirostat": 1,
                "mirostat_tau": 0.8,
                "mirostat_eta": 0.6,
                "penalize_newline": true,
                "stop": ["\n", "user:"],
                "numa": false,
                "num_ctx": 1024,
                "num_batch": 2,
                "num_gpu": 1,
                "main_gpu": 0,
                "low_vram": false,
                "f16_kv": true,
                "vocab_only": false,
                "use_mmap": true,
                "use_mlock": false,
                "num_thread": 8
            }
        }
        """
                .trimIndent()

        // when
        val request = JsonLenient.decodeFromString(OllamaGenerateRequest.serializer(), json)

        // then
        assertEquals("llama3", request.model)
        assertEquals("Why is the sky blue?", request.prompt)
        assertEquals(false, request.stream)
        assertEquals(true, request.raw)
        with(request.options) {
            assertEquals(5, this?.get("num_keep"))
            assertEquals(42, this?.get("seed"))
            assertEquals(100, this?.get("num_predict"))
            assertEquals(20, this?.get("top_k"))
            assertEquals(0.9, this?.get("top_p"))
            assertEquals(0.5, this?.get("tfs_z"))
            assertEquals(0.7, this?.get("typical_p"))
            assertEquals(33, this?.get("repeat_last_n"))
            assertEquals(0.8, this?.get("temperature"))
            assertEquals(1.2, this?.get("repeat_penalty"))
            assertEquals(1.5, this?.get("presence_penalty"))
            assertEquals(1.0, this?.get("frequency_penalty"))
            assertEquals(1, this?.get("mirostat"))
            assertEquals(0.8, this?.get("mirostat_tau"))
            assertEquals(0.6, this?.get("mirostat_eta"))
            assertEquals(true, this?.get("penalize_newline"))
            assertEquals(listOf("\n", "user:"), this?.get("stop"))
            assertEquals(false, this?.get("numa"))
            assertEquals(1024, this?.get("num_ctx"))
            assertEquals(2, this?.get("num_batch"))
            assertEquals(1, this?.get("num_gpu"))
            assertEquals(0, this?.get("main_gpu"))
            assertEquals(false, this?.get("low_vram"))
            assertEquals(true, this?.get("f16_kv"))
            assertEquals(false, this?.get("vocab_only"))
            assertEquals(true, this?.get("use_mmap"))
            assertEquals(false, this?.get("use_mlock"))
            assertEquals(8, this?.get("num_thread"))
        }
    }
}
