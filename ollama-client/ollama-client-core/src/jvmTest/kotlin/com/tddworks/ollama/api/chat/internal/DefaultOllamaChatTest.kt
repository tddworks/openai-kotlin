import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.ollama.api.TestKoinCoroutineExtension
import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatRequest
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.ollama.api.chat.internal.DefaultOllamaChatApi
import com.tddworks.ollama.api.json.JsonLenient
import com.tddworks.ollama.api.mockHttpClient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension

class DefaultOllamaChatTest : KoinTest {
    @JvmField
    @RegisterExtension
    // This extension is used to set the main dispatcher to a test dispatcher
    // launch coroutine eagerly
    // same scheduling behavior as would have in a real app/production
    val testKoinCoroutineExtension = TestKoinCoroutineExtension(StandardTestDispatcher())

    // for kotlin/com/tddworks/common/network/api/ktor/api/Stream.kt required
    // fun json(): Json {
    //    return getInstance()
    // }
    @JvmField
    @RegisterExtension
    val koinTestExtension =
        KoinTestExtension.create { modules(module { single<Json> { JsonLenient } }) }

    @Test
    fun `should return stream of JSON response`() = runTest {
        // Given
        val request =
            OllamaChatRequest(
                model = "llama2",
                messages =
                    listOf(OllamaChatMessage(role = "user", content = "why is the sky blue?")),
            )

        val api =
            DefaultOllamaChatApi(
                DefaultHttpRequester(
                    httpClient =
                        mockHttpClient(
                            "data: { \"model\": \"llama2\", \"created_at\": \"2023-08-04T08:52:19.385406455-07:00\", \"message\": { \"role\": \"assistant\", \"content\": \"The\", \"images\": null }, \"done\": false }"
                        )
                )
            )

        // When
        val responses = api.stream(request).toList()

        // Then
        assertEquals(
            listOf(
                OllamaChatResponse(
                    model = "llama2",
                    createdAt = "2023-08-04T08:52:19.385406455-07:00",
                    message = OllamaChatMessage(role = "assistant", content = "The"),
                    done = false,
                )
            ),
            responses,
        )
    }

    @Test
    fun `should return single JSON response`() = runTest {
        // Given
        val request =
            OllamaChatRequest(
                model = "llama2",
                messages =
                    listOf(OllamaChatMessage(role = "user", content = "why is the sky blue?")),
            )

        val api =
            DefaultOllamaChatApi(
                DefaultHttpRequester(
                    httpClient =
                        mockHttpClient(
                            """
                            {
                              "model": "llama2",
                              "created_at": "2023-12-12T14:13:43.416799Z",
                              "message": {
                                "role": "assistant",
                                "content": "Hello! How are you today?"
                              },
                              "done": true,
                              "total_duration": 5191566416,
                              "load_duration": 2154458,
                              "prompt_eval_count": 26,
                              "prompt_eval_duration": 383809000,
                              "eval_count": 298,
                              "eval_duration": 4799921000
                            }
                            """
                                .trimIndent()
                        )
                )
            )

        // When
        val response = api.request(request)

        // Then
        assertEquals(
            OllamaChatResponse(
                model = "llama2",
                createdAt = "2023-12-12T14:13:43.416799Z",
                message =
                    OllamaChatMessage(role = "assistant", content = "Hello! How are you today?"),
                done = true,
                totalDuration = 5191566416,
                loadDuration = 2154458,
                promptEvalCount = 26,
                promptEvalDuration = 383809000,
                evalCount = 298,
                evalDuration = 4799921000,
            ),
            response,
        )
    }
}
