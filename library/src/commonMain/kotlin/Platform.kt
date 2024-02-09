import io.ktor.client.engine.*

interface Platform {
    val name: String
    val engine: HttpClientEngineFactory<HttpClientEngineConfig>
}

expect fun getPlatform(): Platform