import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.ollama.api.TestKoinCoroutineExtension
import com.tddworks.ollama.api.generate.OllamaGenerateRequest
import com.tddworks.ollama.api.generate.OllamaGenerateResponse
import com.tddworks.ollama.api.generate.internal.DefaultOllamaGenerateApi
import com.tddworks.ollama.api.json.JsonLenient
import com.tddworks.ollama.api.mockHttpClient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension

class DefaultOllamaGenerateTest : KoinTest {
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
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single<Json> { JsonLenient }
            })
    }

    @Test
    fun `should return stream of JSON response and done`() = runTest {
        // Given
        val request = OllamaGenerateRequest(
            model = "llama2",
            prompt = "why is the sky blue?"
        )

        val api = DefaultOllamaGenerateApi(
            DefaultHttpRequester(
                httpClient = mockHttpClient("data: {\"model\":\"deepseek-coder:6.7b\",\"created_at\":\"2024-06-19T04:21:28.204638Z\",\"response\":\"\",\"done\":true,\"done_reason\":\"length\",\"total_duration\":7864500542,\"load_duration\":5949281959,\"prompt_eval_count\":181,\"prompt_eval_duration\":308480000,\"eval_count\":100,\"eval_duration\":1603405000}")
            )
        )

        // When
        val responses = api.stream(request).toList()

        // Then
        assertEquals(
            listOf(
                OllamaGenerateResponse(
                    model = "deepseek-coder:6.7b",
                    createdAt = "2024-06-19T04:21:28.204638Z",
                    response = "",
                    done = true,
                    doneReason = "length",
                    totalDuration = 7864500542,
                    loadDuration = 5949281959,
                    promptEvalCount = 181,
                    promptEvalDuration = 308480000,
                    evalCount = 100,
                    evalDuration = 1603405000
                )
            ), responses
        )
    }

    @Test
    fun `should return stream of JSON response and not done`() = runTest {
        // Given
        val request = OllamaGenerateRequest(
            model = "deepseek-coder:6.7b",
            prompt = "why is the sky blue?"
        )

        val api = DefaultOllamaGenerateApi(
            DefaultHttpRequester(
                httpClient = mockHttpClient("data: {\"model\":\"deepseek-coder:6.7b\",\"created_at\":\"2024-06-19T04:21:28.188452Z\",\"response\":\"\\n\",\"done\":false}")
            )
        )

        // When
        val responses = api.stream(request).toList()

        // Then
        assertEquals(
            listOf(
                OllamaGenerateResponse(
                    model = "deepseek-coder:6.7b",
                    createdAt = "2024-06-19T04:21:28.188452Z",
                    response = "\n",
                    done = false
                )
            ), responses
        )
    }

    @Test
    fun `should return single JSON response`() = runTest {
        // Given
        val request = OllamaGenerateRequest(
            model = "llama2",
            prompt = "why is the sky blue?"
        )


        val api = DefaultOllamaGenerateApi(
            DefaultHttpRequester(
                httpClient = mockHttpClient(
                    """
                     {
                      "model": "llama3",
                      "created_at": "2023-08-04T19:22:45.499127Z",
                      "response": "Hello! How are you today?",
                      "done": true,
                      "context": [
                        1,
                        2,
                        3
                      ],
                      "total_duration": 10706818083,
                      "load_duration": 6338219291,
                      "prompt_eval_count": 26,
                      "prompt_eval_duration": 130079000,
                      "eval_count": 259,
                      "eval_duration": 4232710000
                    }
                """.trimIndent()
                )
            )
        )

        // When
        val response = api.request(request)

        // Then
        assertEquals(
            OllamaGenerateResponse(
                model = "llama3",
                createdAt = "2023-08-04T19:22:45.499127Z",
                response = "Hello! How are you today?",
                context = listOf(1, 2, 3),
                done = true,
                totalDuration = 10706818083,
                loadDuration = 6338219291,
                promptEvalCount = 26,
                promptEvalDuration = 130079000,
                evalCount = 259,
                evalDuration = 4232710000L
            ), response
        )
    }
}

